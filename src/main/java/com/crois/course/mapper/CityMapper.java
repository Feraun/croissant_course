package com.crois.course.mapper;

import com.crois.course.dto.CityDTO.CityDTO;
import com.crois.course.entity.CityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CityMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "institutions", ignore = true)
    CityEntity createEntityFromDTO(CityDTO cityDTO);

    CityDTO createDtoFromEntity(CityEntity cityEntity);

}
