package com.crois.course.mapper;

import com.crois.course.dto.CategoryInstitutionDTO.CategoryInstitutionDTO;
import com.crois.course.entity.CategoryInstitutionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryInstitutionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "institutions", ignore = true)
    CategoryInstitutionEntity createEntityFromDTO(CategoryInstitutionDTO categoryInstitutionDTO);

    CategoryInstitutionDTO createDtoFromEntity(CategoryInstitutionEntity categoryInstitutionEntity);

}
