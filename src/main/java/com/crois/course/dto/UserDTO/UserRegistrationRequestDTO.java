package com.crois.course.dto.UserDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegistrationRequestDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @Email
    private String email;

    private String firstName;

    private String lastName;
}