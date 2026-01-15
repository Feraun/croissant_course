package com.crois.course.mapper;

import com.crois.course.dto.BoxDTO.BoxShortResponseDTO;
import com.crois.course.dto.BoxDTO.CreateBoxDTO;
import com.crois.course.dto.BoxDTO.RandomBoxResponseDTO;
import com.crois.course.entity.BoxEntity;
import com.crois.course.entity.BoxStatus;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BoxMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "boxStatus", ignore = true)
    BoxEntity createEntityFromDtoForNewBox(CreateBoxDTO createBoxDTO);

    @Mapping(target = "institutionId", source="institution.id")
    CreateBoxDTO createDtoFromEntity(BoxEntity boxEntity);


    BoxShortResponseDTO createShortDtoFromEntity(BoxEntity boxEntity);

    RandomBoxResponseDTO createManagersDtoFromEntity(BoxEntity boxEntity);

    @AfterMapping
    default void setRoleForNewBox(@MappingTarget BoxEntity box) {
            box.setBoxStatus(BoxStatus.FOR_SALE);
    }
}
