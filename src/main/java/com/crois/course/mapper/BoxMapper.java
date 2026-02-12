package com.crois.course.mapper;

import com.crois.course.dto.BoxDTO.BoxResponseDTO;
import com.crois.course.dto.BoxDTO.CreateBoxDTO;
import com.crois.course.entity.BoxEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BoxMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "institution", ignore = true)
    BoxEntity createEntityFromDtoForNewBox(CreateBoxDTO createBoxDTO, Long institutionId);

    @Mapping(target = "randomly", ignore = true)
    @Mapping(target = "quantity", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "institution", ignore = true)
    @Mapping(target = "institutionId", ignore = true)
    BoxEntity updateBox(@MappingTarget BoxEntity boxEntity, CreateBoxDTO createBoxDTO);

    CreateBoxDTO createDtoFromEntity(BoxEntity boxEntity);

    BoxResponseDTO createShortDtoFromEntity(BoxEntity boxEntity);

    BoxResponseDTO createManagersDtoFromEntity(BoxEntity boxEntity);

}
