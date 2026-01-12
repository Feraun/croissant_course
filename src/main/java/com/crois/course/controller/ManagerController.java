package com.crois.course.controller;

import com.crois.course.dto.BoxDTO.CreateBoxDTO;
import com.crois.course.service.ManagerService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manager/")
public class ManagerController {


    private final ManagerService managerService;


    public ManagerController(ManagerService managerService){
        this.managerService = managerService;
    }

    @PostMapping(value = "institutions/{institutionId}/createBox", produces = MediaType.APPLICATION_JSON_VALUE)
    public CreateBoxDTO createBox(@PathVariable("institutionId") Long institutionId, @RequestBody CreateBoxDTO createBoxDTO, Authentication authentication){
        return managerService.createBoxByManager(institutionId, createBoxDTO, authentication);
    }


}
