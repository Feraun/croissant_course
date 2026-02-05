package com.crois.course.dto.UserDTO;

import com.crois.course.enums.Role;

import java.util.List;

public record UserFullDTO(
        Long id,
        String name,
        String firstName,
        String lastName,
        String contactNumber,
        String email,
        String createdAt,
        Role role
) {
}
