package com.crois.course.dto.UserDTO.Login;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequestDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}