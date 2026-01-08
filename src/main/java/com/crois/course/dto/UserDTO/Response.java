package com.crois.course.dto.UserDTO;


import com.crois.course.dto.UserDTO.Login.UserLoginResponse;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class Response implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String token;

    public Response(String token) {
        this.token = token;
    }

    public static Response ok(UserLoginResponse jwtResponse) {
        return new Response(jwtResponse.token());
    }

}
