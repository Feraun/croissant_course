package com.crois.course.controller;


import com.crois.course.dto.UserDTO.UserRegistrationRequestDTO;
import com.crois.course.service.RegistrationService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;


    @PostMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserRegistrationRequestDTO createUser(@RequestBody UserRegistrationRequestDTO dto){
        return registrationService.createUser(dto);
    }

}
