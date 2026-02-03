package com.crois.course.controller.ClientControllers;

import com.crois.course.dto.UserDTO.UserProfileDTO;
import com.crois.course.service.ClientServices.ClientProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client/profile")
@AllArgsConstructor
public class ClientProfileController {

    private final ClientProfileService clientProfileService;
    @GetMapping()
    public UserProfileDTO getDataForProfilePage(Authentication authentication){
        return clientProfileService.getDataForProfilePage(authentication);
    }
}
