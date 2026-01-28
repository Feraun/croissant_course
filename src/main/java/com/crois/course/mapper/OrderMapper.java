package com.crois.course.mapper;

import com.crois.course.dto.OrderDTO;
import com.crois.course.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "user", source = "user")
    @Mapping(target = "box", source = "box")
    OrderDTO createDtoFromEntity(OrderEntity orderEntity);

}
