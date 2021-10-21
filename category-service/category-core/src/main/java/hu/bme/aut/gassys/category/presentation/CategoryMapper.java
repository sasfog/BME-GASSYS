package hu.bme.aut.gassys.category.presentation;

import hu.bme.aut.gassys.category.CategoryCreationDTO;
import hu.bme.aut.gassys.category.CategoryDTO;
import hu.bme.aut.gassys.category.data.CategoryEntity;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO categoryToCategoryDTO(CategoryEntity categoryEntity);
    CategoryCreationDTO categoryToCategoryCreationDTO(CategoryEntity categoryEntity);
    CategoryCreationDTO categoryDTOToCategoryCreationDTO(CategoryDTO categoryDTO);
}