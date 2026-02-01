package com.crois.course.controller.ManagerControllers;

import com.crois.course.dto.BoxDTO.CreateBoxDTO;
import com.crois.course.dto.BoxDTO.RandomBoxResponseDTO;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseManagerForGetAll;
import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
import com.crois.course.service.ManagerServices.ManagerBoxService;
import com.crois.course.service.ManagerServices.ManagerInstitutionService;
import lombok.AllArgsConstructor;
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
    public CreateBoxDTO createBox(@PathVariable("institutionId") Long institutionId, @RequestBody CreateBoxDTO createBoxDTO, Authentication authentication){
        return managerBoxService.createBoxByManager(institutionId, createBoxDTO, authentication);
    }

    @GetMapping(value = "/{institutionId}/boxes", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResult<RandomBoxResponseDTO> searchBoxes(
            @PathVariable("institutionId") Long institutionId,

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

        return managerBoxService.getAllBox(institutionId, name, params, authentication);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResult<InstitutionResponseManagerForGetAll> getMyInstitutions(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "asc") String direction,

            Authentication authentication
    ){

        PageParams params = new PageParams(
                page,
                size,
                sortBy,
                direction.equalsIgnoreCase("asc")
        );

        return managerInstitutionService.getMyInstitutions(name, params, authentication);
    }

}
