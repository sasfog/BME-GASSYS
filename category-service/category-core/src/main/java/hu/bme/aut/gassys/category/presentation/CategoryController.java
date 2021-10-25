package hu.bme.aut.gassys.category.presentation;

import feign.FeignException;
import hu.bme.aut.gassys.category.CategoryCreationDTO;
import hu.bme.aut.gassys.category.CategoryDTO;
import hu.bme.aut.gassys.category.data.CategoryEntity;
import hu.bme.aut.gassys.category.exception.CategoryException;
import hu.bme.aut.gassys.category.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
@Slf4j
public class CategoryController {

    private CategoryService categoryService;

    private CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> getAll(Pageable pageable){
        return ResponseEntity.ok(categoryService.findAllCategories(pageable).map(categoryMapper::categoryToCategoryDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Integer id){
        return categoryService.findOne(id)
                .map(categoryEntity -> ResponseEntity.ok(categoryMapper.categoryToCategoryDTO(categoryEntity)))
                .orElseGet( () -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryCreationDTO categoryCreationDTO, UriComponentsBuilder builder){
        try{
            CategoryEntity categoryEntity = categoryService.create(categoryCreationDTO);

            UriComponents uriComponents = builder.path("/api/category/{id}").buildAndExpand(categoryEntity.getId());
            return ResponseEntity.created(uriComponents.toUri()).body(categoryMapper.categoryToCategoryDTO(categoryEntity));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAll(){
        try {
            categoryService.deleteAll();
            return ResponseEntity.ok(HttpStatus.OK);
        }
        catch (CategoryException e){
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Integer id){
        try {
            categoryService.deleteOne(id);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        catch (FeignException e){
            return ResponseEntity.internalServerError().build();
        }
        catch (CategoryException e){
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> modifyCategory(@PathVariable Integer id, @RequestBody CategoryDTO categoryDTO, UriComponentsBuilder builder){
        try {


            CategoryEntity categoryEntity;
            if (!categoryService.existsById(id)){
                categoryEntity = categoryService.create(categoryMapper.categoryDTOToCategoryCreationDTO(categoryDTO));
                UriComponents uriComponents = builder.path("/api/category/{id}").buildAndExpand(categoryEntity.getId());
                log.debug("Created entity : {}", categoryEntity);
                return ResponseEntity.created(uriComponents.toUri()).body(categoryMapper.categoryToCategoryDTO(categoryEntity));
            }
            else {
                categoryEntity = categoryService.modify(id, categoryDTO);
                log.debug("Updated entity: {}", categoryEntity);
                return ResponseEntity.ok(categoryMapper.categoryToCategoryDTO(categoryEntity));
            }
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
