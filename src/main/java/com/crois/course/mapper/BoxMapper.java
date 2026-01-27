package com.crois.course.mapper;

import com.crois.course.dto.BoxDTO.BoxShortResponseDTO;
import com.crois.course.dto.BoxDTO.CreateBoxDTO;
import com.crois.course.dto.BoxDTO.RandomBoxResponseDTO;
import com.crois.course.entity.BoxEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BoxMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "institution.id", source = "institutionId")
    BoxEntity createEntityFromDtoForNewBox(CreateBoxDTO createBoxDTO);

    @Mapping(target = "institutionId", source="institution.id")
    CreateBoxDTO createDtoFromEntity(BoxEntity boxEntity);


    BoxShortResponseDTO createShortDtoFromEntity(BoxEntity boxEntity);

    RandomBoxResponseDTO createManagersDtoFromEntity(BoxEntity boxEntity);

}
