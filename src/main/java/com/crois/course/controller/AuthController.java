package com.crois.course.controller;


import com.crois.course.dto.UserDTO.Login.UserLoginRequestDTO;
import com.crois.course.dto.UserDTO.Login.UserLoginResponse;
import com.crois.course.dto.UserDTO.UserRegistrationRequestDTO;
import com.crois.course.service.KeycloakService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest-api")
@RequiredArgsConstructor
public class AuthController {

    private final KeycloakService keycloakService;

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@Valid @RequestBody UserLoginRequestDTO request) {
        return ResponseEntity.ok(keycloakService.login(request));
    }

    @PostMapping("/registration")
    public ResponseEntity<String> register(@Valid @RequestBody UserRegistrationRequestDTO request) {
        keycloakService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Пользователь успешно зарегистрирован");
    }
}
