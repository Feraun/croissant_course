package com.crois.course.controller.ManagerControllers;

import com.crois.course.dto.BoxDTO.CreateBoxDTO;
import com.crois.course.dto.BoxDTO.RandomBoxResponseDTO;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseManagerForGetAll;
import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
import com.crois.course.dto.UserDTO.AuthUser;
import com.crois.course.service.ManagerServices.ManagerBoxService;
import com.crois.course.service.ManagerServices.ManagerInstitutionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manager/institutions")
@AllArgsConstructor
public class ManagerInstitutionController {

    private final ManagerBoxService managerBoxService;
    private final ManagerInstitutionService managerInstitutionService;

    @PostMapping(value = "/{institutionId}/createBox", produces = MediaType.APPLICATION_JSON_VALUE)
    public CreateBoxDTO createBox(
            @PathVariable("institutionId") Long institutionId,
            @RequestBody CreateBoxDTO createBoxDTO,
            Authentication authentication){
        return managerBoxService.createBoxByManager(institutionId, createBoxDTO, authentication);
    }

    @GetMapping(value = "/{institutionId}/boxes", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<RandomBoxResponseDTO> searchBoxes(
            @PathVariable("institutionId") Long institutionId,
            @RequestParam(required = false) Long boxId,
            @PageableDefault Pageable pageable,
            Authentication authentication
    ) {

        return managerBoxService.getAllBox(institutionId, boxId, authentication, pageable);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<InstitutionResponseManagerForGetAll> getMyInstitutions(
            @RequestParam(required = false) String name,
            @PageableDefault Pageable pageable,
            Authentication authentication
    ){
        return managerInstitutionService.getMyInstitutions(name, pageable, authentication);
    }

}
