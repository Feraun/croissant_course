package com.crois.course.mapper;
import com.crois.course.dto.UserDTO.UserProfileDTO;
import com.crois.course.dto.UserDTO.UserRegistrationRequestDTO;
import com.crois.course.entity.Role;
import com.crois.course.entity.UserEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserRegistrationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target =  "enabled", constant = "true")
    @Mapping(target = "roles", ignore = true )
    UserEntity toUserRegistrationEntity(UserRegistrationRequestDTO dto);

    @Mapping(target = "password", ignore = true)
    UserRegistrationRequestDTO toUserRegistrationDTO(UserEntity entity);

    @AfterMapping
    default void setRolesFromDTO(UserRegistrationRequestDTO dto, @MappingTarget UserEntity user) {
        if (dto.roles() != null && !dto.roles().isEmpty()) {
            List<Role> roles = dto.roles().stream()
                    .map(String::toUpperCase)
                    .map(Role::valueOf)
                    .toList();
            user.setRoles(roles);
        } else {
            user.setRoles(List.of(Role.CLIENT));
        }
    }

    UserProfileDTO createUserProfileDtoFromEntity(UserEntity userEntity);
}