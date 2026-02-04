package com.crois.course.controller.AdminControllers;

import com.crois.course.dto.UserDTO.UserDTOForAddManagerToInstitution;
import com.crois.course.service.AdminServices.AdminUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@AllArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping(value = "/getAll")
    public List<UserDTOForAddManagerToInstitution> getAllUsers(){
        return adminUserService.getAllUsers();
    }

}
