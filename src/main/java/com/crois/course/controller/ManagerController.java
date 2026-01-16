package com.crois.course.controller;

import com.crois.course.dto.BoxDTO.CreateBoxDTO;
import com.crois.course.dto.BoxDTO.RandomBoxResponseDTO;
import com.crois.course.dto.CategoryInstitutionDTO.CategoryInstitutionDTO;
import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
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

    @GetMapping(value = "institutions/{institutionId}/boxes", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResult<RandomBoxResponseDTO> searchBoxes(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "asc") String direction,

            Authentication authentication
    ) {
        PageParams params = new PageParams(
                page,
                size,
                sortBy,
                direction.equalsIgnoreCase("asc")
        );

        return managerService.getAllBox(name, params, authentication);
    }
}
