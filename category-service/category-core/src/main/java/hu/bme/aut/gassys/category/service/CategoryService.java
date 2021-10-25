package hu.bme.aut.gassys.category.service;

import feign.FeignException;
import hu.bme.aut.gassys.appointment.AppointmentServiceIF;
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

    private AppointmentServiceIF appointmentServiceClient;

    public CategoryEntity create(CategoryCreationDTO dto){
        log.debug("Creating new Category {}", dto.getName());
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
        log.debug("Deleting category {}", id);
        try {
            appointmentServiceClient.removeApplicantAndCategoryFromAllAppointment(null, id);
            categoryRepository.deleteById(id);
        }
        catch (FeignException e) {
            e.printStackTrace();
            log.error("Error during removal of category {} from appointments", id);
            log.error("{}",e.getMessage());
            throw e;
        }
        catch (EmptyResultDataAccessException e){
            log.warn("Category with id {} not found.", id);
            throw new CategoryException();
        }
    }

    public void deleteAll() {
        log.debug("Deleting all categories");
        categoryRepository.deleteAll();
    }

    public boolean existsById(Integer id) {
        return categoryRepository.existsById(id);
    }

    public CategoryEntity modify(Integer id, CategoryDTO categoryDTO) {
        log.debug("Updating category {} to {}", id, categoryDTO.getName());

        CategoryEntity entity = categoryRepository.findById(id).orElseThrow(CategoryException::new);
        entity.setName(categoryDTO.getName());
        return categoryRepository.save(entity);
    }
}
