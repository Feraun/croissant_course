package com.crois.course.dto.UserDTO.Login;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserLoginResponse {

    private String accessToken;

    private String refreshToken;

    private Long expiresIn;

    private String tokenType;
}