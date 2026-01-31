package com.crois.course.dto.UserDTO;

import java.util.List;

public record UserFullDTO(
        Long id,
        String name,
        String firstName,
        String lastName,
        String contactNumber,
        String email,
        String createdAt,
        List<String> roles
) {
}
