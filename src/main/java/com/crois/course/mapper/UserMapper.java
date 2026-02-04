package com.crois.course.mapper;
import com.crois.course.dto.UserDTO.UserDTOForAddManagerToInstitution;
import com.crois.course.dto.UserDTO.UserProfileDTO;
import com.crois.course.dto.UserDTO.UserRegistrationRequestDTO;
import com.crois.course.enums.Role;
import com.crois.course.entity.UserEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target =  "enabled", constant = "true")
    @Mapping(target = "role", ignore = true )
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "institutionUnderUser", ignore = true)
    UserEntity toUserRegistrationEntity(UserRegistrationRequestDTO dto);

    @Mapping(target = "password", ignore = true)
    UserRegistrationRequestDTO toUserRegistrationDTO(UserEntity entity);

    UserProfileDTO createUserProfileDtoFromEntity(UserEntity userEntity);

    UserDTOForAddManagerToInstitution createUserForAddToInstDTO(UserEntity userEntity);
}