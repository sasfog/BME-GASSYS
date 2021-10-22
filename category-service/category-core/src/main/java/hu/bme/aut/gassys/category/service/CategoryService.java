package hu.bme.aut.gassys.category.service;

import hu.bme.aut.gassys.category.CategoryCreationDTO;
import hu.bme.aut.gassys.category.CategoryDTO;
import hu.bme.aut.gassys.category.data.CategoryEntity;
import hu.bme.aut.gassys.category.data.CategoryRepository;
import hu.bme.aut.gassys.category.exception.CategoryException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryEntity create(CategoryCreationDTO dto){
        log.debug("Creating new Category {}", dto);
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(dto.getName());
        return categoryRepository.save(categoryEntity);
    }

    public Page<CategoryEntity> findAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Optional<CategoryEntity> findOne(Integer id) {
        return categoryRepository.findById(id);
    }

    public void deleteOne(Integer id){
        log.debug("Creating category {}", id);
        try {
            categoryRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e){
            log.warn("Category with id {} not found.", id);
            throw new CategoryException();
        }
    }

    public void deleteAll() {
        log.debug("Creating all categories");
        categoryRepository.deleteAll();
    }

    public boolean existsById(Integer id) {
        return categoryRepository.existsById(id);
    }

    public CategoryEntity modify(Integer id, CategoryDTO categoryDTO) {
        log.debug("Updating category {} to {}", id, categoryDTO.getName());

        CategoryEntity entity = categoryRepository.findById(id).get();
        entity.setName(categoryDTO.getName());
        return categoryRepository.save(entity);
    }
}
