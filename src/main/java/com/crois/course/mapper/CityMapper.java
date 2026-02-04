package com.crois.course.mapper;

import com.crois.course.dto.CityDTO.CityDTO;
import com.crois.course.entity.CityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CityMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "institutions", ignore = true)
    CityEntity createEntityFromDTO(CityDTO cityDTO);

    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "institutions", ignore = true)
    CityEntity updateEntity(@MappingTarget CityEntity cityEntity,
                            CityDTO cityDTO);

    CityDTO createDtoFromEntity(CityEntity cityEntity);

}
