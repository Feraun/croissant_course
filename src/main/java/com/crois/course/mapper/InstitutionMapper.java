package com.crois.course.mapper;

import com.crois.course.dto.InstitutionDTO.*;
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

    InstitutionResponseClientGetAll createDtoForClient(InstitutionEntity institution);

    @Mapping(target = "name", source = "institution.name")
    @Mapping(target = "address", source = "institution.address")
    @Mapping(target = "rating", source = "institution.rating")
    @Mapping(target = "contactNumber", source = "institution.contactNumber")
    @Mapping(target = "categories", source = "institution.categories")
    @Mapping(target = "boxes", source = "boxEntities")
    InstitutionResponseClient createDtoForClientById(InstitutionEntity institution, List<BoxEntity> boxEntities);

    @Mapping(target = "logoImage", source = "logo.id")
    @Mapping(target = "cityName", source = "city.name")
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
