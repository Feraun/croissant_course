package com.crois.course.mapper;

import com.crois.course.dto.InstitutionDTO.InstitutionRequestDTO;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseClient;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseDTO;
import com.crois.course.entity.InstitutionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InstitutionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "categories", ignore = true)
    InstitutionEntity createEntityFromDTO(InstitutionRequestDTO dto);

    InstitutionResponseClient createDtoForClient(InstitutionEntity institution);

    InstitutionResponseDTO createDtoFromEntity(InstitutionEntity institution);
}
