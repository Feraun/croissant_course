package com.crois.course.controller.AdminControllers;

import com.crois.course.dto.UserDTO.UserDTOForAddManagerToInstitution;
import com.crois.course.dto.UserDTO.UserFullDTO;
import com.crois.course.service.AdminServices.AdminUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/users")
@AllArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping()
    public List<UserFullDTO> getAllUsers(){
        return adminUserService.getAllUsers();
    }

    @GetMapping(value = "/getUsersForManagers")
    public List<UserDTOForAddManagerToInstitution> getUsersForManagers() {
        return adminUserService.getUsersForManagers();
    }

    @GetMapping(value = "/{id}")
    public UserFullDTO getUserById(@PathVariable("id") UUID id) {
        return adminUserService.getUserById(id);
    }
    //todo editRoleUser чтобы выдавать роль менеджера

}
