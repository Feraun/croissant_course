package com.crois.course.controller;

import com.crois.course.dto.UserDTO.Login.UserLoginRequestDTO;
import com.crois.course.dto.UserDTO.Response;
import com.crois.course.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@Slf4j
@AllArgsConstructor
public class AuthorizationController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Response createToken(@RequestBody UserLoginRequestDTO request) throws Exception {
        return authService.createToken(request);
    }

}
