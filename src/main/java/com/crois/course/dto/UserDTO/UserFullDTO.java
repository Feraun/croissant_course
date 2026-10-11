package com.crois.course.dto.UserDTO;

import com.crois.course.enums.Role;

public record UserFullDTO(
        String id,
        String name,
        String firstName,
        String lastName,
        String contactNumber,
        String email,
        String createdAt,
        Role role
) {
}
