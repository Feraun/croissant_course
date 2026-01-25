package com.crois.course.dto.UserDTO;

public record UserProfileDTO(
        String username,
        String firstName,
        String lastName,
        String email,
        String contactNumber,
        String createdAt
) {
}
