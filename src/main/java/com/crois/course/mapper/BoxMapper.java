package com.crois.course.mapper;

import com.crois.course.dto.BoxDTO.BoxShortResponseDTO;
import com.crois.course.dto.BoxDTO.CreateBoxDTO;
import com.crois.course.dto.BoxDTO.RandomBoxResponseDTO;
import com.crois.course.entity.BoxEntity;
import com.crois.course.entity.InstitutionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BoxMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "institution", source = "institution")
    @Mapping(target = "name", source = "createBoxDTO.name")
    @Mapping(target = "description", source = "createBoxDTO.description")
    @Mapping(target = "price" , source = "createBoxDTO.price")
    @Mapping(target = "quantity", source = "createBoxDTO.quantity")
    BoxEntity createEntityFromDtoForNewBox(CreateBoxDTO createBoxDTO, InstitutionEntity institution);

    CreateBoxDTO createDtoFromEntity(BoxEntity boxEntity);


    BoxShortResponseDTO createShortDtoFromEntity(BoxEntity boxEntity);

    RandomBoxResponseDTO createManagersDtoFromEntity(BoxEntity boxEntity);

}
