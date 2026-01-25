package com.crois.course.mapper;

import com.crois.course.dto.OrderDTO;
import com.crois.course.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "boxId", source = "box.id")
    OrderDTO createDtoFromEntity(OrderEntity orderEntity);

}
