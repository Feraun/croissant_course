package com.crois.course.controller;

import com.crois.course.dto.BoxDTO.CreateBoxDTO;
import com.crois.course.dto.BoxDTO.RandomBoxResponseDTO;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseManagerForGetAll;
import com.crois.course.dto.OrderDTO;
import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
import com.crois.course.service.ManagerService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manager/")
@AllArgsConstructor
public class ManagerController {


    private final ManagerService managerService;


    @PostMapping(value = "institutions/{institutionId}/createBox", produces = MediaType.APPLICATION_JSON_VALUE)
    public CreateBoxDTO createBox(@PathVariable("institutionId") Long institutionId, @RequestBody CreateBoxDTO createBoxDTO, Authentication authentication){
        return managerService.createBoxByManager(institutionId, createBoxDTO, authentication);
    }

    @GetMapping(value = "institutions/{institutionId}/boxes", produces = MediaType.APPLICATION_JSON_VALUE)
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

        return managerService.getAllBox(institutionId, name, params, authentication);
    }

    @GetMapping(value = "institutions", produces = MediaType.APPLICATION_JSON_VALUE)
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

        return managerService.getMyInstitutions(name, params, authentication);
    }

    @GetMapping(value = "orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResult<OrderDTO> getAllOrders(
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

        return managerService.searchOrders(name, params, authentication);
    }
}
