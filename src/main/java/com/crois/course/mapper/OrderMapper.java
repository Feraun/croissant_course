package com.crois.course.mapper;

import com.crois.course.dto.OrderDTO;
import com.crois.course.entity.BoxEntity;
import com.crois.course.entity.OrderEntity;
import com.crois.course.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "user", source = "user")
    @Mapping(target = "box", source = "box")
    OrderDTO createDtoFromEntity(OrderEntity orderEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "userEntity")
    @Mapping(target = "box", source = "boxEntity")
    @Mapping(target = "createdAt", source = "localDateTime")
    @Mapping(target = "amount", source = "boxEntity.price")
    OrderEntity createEntity(UserEntity userEntity,
                             BoxEntity boxEntity,
                             LocalDateTime localDateTime);

}
