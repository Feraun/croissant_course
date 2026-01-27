package com.crois.course.mapper;

import com.crois.course.dto.InstitutionDTO.InstitutionRequestDTO;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseClient;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseDTO;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseManagerForGetAll;
import com.crois.course.entity.InstitutionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InstitutionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "managers", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "boxes", ignore = true)
    @Mapping(target = "logo", ignore = true)
    InstitutionEntity createEntityFromDTO(InstitutionRequestDTO dto);

    InstitutionResponseClient createDtoForClient(InstitutionEntity institution);

    @Mapping(target = "logoImage", source = "logo.id")
    InstitutionResponseDTO createDtoFromEntity(InstitutionEntity institution);


    InstitutionResponseManagerForGetAll createManagerDtoFromEntity(InstitutionEntity institution);
}
