package com.crois.course.mapper;

import com.crois.course.dto.UserDTO.UserDTOForAddManagerToInstitution;
import com.crois.course.dto.UserDTO.UserFullDTO;
import com.crois.course.dto.UserDTO.UserProfileDTO;
import com.crois.course.dto.UserDTO.UserRegistrationRequestDTO;
import com.crois.course.entity.UserEntity;
import com.crois.course.enums.Role;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "role", ignore = true )
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "institutionUnderUser", ignore = true)
    UserEntity toUserRegistrationEntity(UserRegistrationRequestDTO dto, String id);

    @AfterMapping
    default void afterMapping(UserRegistrationRequestDTO dto,
                              @MappingTarget UserEntity target) {
        target.setCreatedAt(LocalDateTime.now());
        target.setRole(Role.CLIENT);
    }

    UserProfileDTO createUserProfileDtoFromEntity(UserEntity userEntity);

    UserDTOForAddManagerToInstitution createUserForAddManagerToInstitutionDTO(UserEntity userEntity);

    UserFullDTO toUserFullDTO(UserEntity userEntity);
}