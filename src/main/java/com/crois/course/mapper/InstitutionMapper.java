package com.crois.course.mapper;

import com.crois.course.dto.InstitutionDTO.InstitutionRequestDTO;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseClient;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseDTO;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseManagerForGetAll;
import com.crois.course.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface InstitutionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "boxes", ignore = true)
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "address", source = "dto.address")
    @Mapping(target = "rating", source = "dto.rating")
    @Mapping(target = "contactNumber", source = "dto.contactNumber")
    @Mapping(target = "city", source = "cityEntity")
    @Mapping(target = "categories", source = "categoryInstitutionEntityList")
    @Mapping(target = "manager", source = "manager")
    @Mapping(target = "createdAt", source = "localDateTime")
    @Mapping(target = "logo", source = "imageEntity")
    InstitutionEntity createEntityFromDTO(InstitutionRequestDTO dto,
                                          List<CategoryInstitutionEntity> categoryInstitutionEntityList,
                                          UserEntity manager,
                                          LocalDateTime localDateTime,
                                          ImageEntity imageEntity,
                                          CityEntity cityEntity
    );

    InstitutionResponseClient createDtoForClient(InstitutionEntity institution);

    @Mapping(target = "logoImage", source = "logo.id")
    InstitutionResponseDTO createDtoFromEntity( InstitutionEntity institution);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "boxes", ignore = true)
    @Mapping(target = "logo", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "address", source = "dto.address")
    @Mapping(target = "rating", source = "dto.rating")
    @Mapping(target = "contactNumber", source = "dto.contactNumber")
    @Mapping(target = "city", source = "cityEntity")
    @Mapping(target = "categories", source = "categoryInstitutionEntityList")
    @Mapping(target = "manager", source = "manager")
    InstitutionEntity updateEntity(@MappingTarget InstitutionEntity institutionEntity,
                                   InstitutionRequestDTO dto,
                                   List<CategoryInstitutionEntity> categoryInstitutionEntityList,
                                   UserEntity manager,
                                   CityEntity cityEntity
    );


    InstitutionResponseManagerForGetAll createManagerDtoFromEntity(InstitutionEntity institution);
}
