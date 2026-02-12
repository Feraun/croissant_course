package com.crois.course.mapper;

import com.crois.course.dto.InstitutionDTO.*;
import com.crois.course.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface InstitutionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "boxes", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "logo", ignore = true)
    @Mapping(target = "categories", source = "categoryInstitutionEntityList")
    InstitutionEntity createEntityFromDTO(InstitutionRequestDTO dto,
                                          List<CategoryInstitutionEntity> categoryInstitutionEntityList,
                                          Long managerId,
                                          LocalDateTime createdAt,
                                          UUID logoId,
                                          Long cityId
    );

    InstitutionResponseClientGetAll createDtoForClient(InstitutionEntity institution);

    @Mapping(target = "name", source = "institution.name")
    @Mapping(target = "address", source = "institution.address")
    @Mapping(target = "rating", source = "institution.rating")
    @Mapping(target = "contactNumber", source = "institution.contactNumber")
    @Mapping(target = "categories", source = "institution.categories")
    @Mapping(target = "boxes", source = "boxEntities")
    InstitutionResponseClient createDtoForClientById(InstitutionEntity institution, List<BoxEntity> boxEntities);

    @Mapping(target = "cityName", source = "city.name")
    InstitutionResponseDTO createDtoFromEntity( InstitutionEntity institution);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "boxes", ignore = true)
    @Mapping(target = "logo", ignore = true)
    @Mapping(target = "logoId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "categories", source = "categoryInstitutionEntityList")
    InstitutionEntity updateEntity(@MappingTarget InstitutionEntity institutionEntity,
                                   InstitutionRequestDTO dto,
                                   List<CategoryInstitutionEntity> categoryInstitutionEntityList,
                                   Long managerId,
                                   Long cityId
    );


    InstitutionResponseManagerForGetAll createManagerDtoFromEntity(InstitutionEntity institution);
}
