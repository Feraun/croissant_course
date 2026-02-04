package com.crois.course.dto.UserDTO;

import java.util.List;

public record UserRegistrationRequestDTO (
        String email,
        String password,
        String username,
        String contactNumber,
        String firstName,
        String lastName
) { }