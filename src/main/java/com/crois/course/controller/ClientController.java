package com.crois.course.controller;

import com.crois.course.dto.BoxDTO.BoxShortResponseDTO;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseClient;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseDTO;
import com.crois.course.dto.OrderDTO;
import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
import com.crois.course.dto.UserDTO.UserProfileDTO;
import com.crois.course.service.ClientServices.ClientBoxService;
import com.crois.course.service.ClientServices.ClientInstitutionService;
import com.crois.course.service.ClientServices.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client/")
@AllArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final ClientInstitutionService clientInstitutionService;
    private final ClientBoxService clientBoxService;


    @GetMapping("/institutions")
    public PageResult<InstitutionResponseClient> searchInstitutions(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        PageParams params = new PageParams(
                page,
                size,
                sortBy,
                direction.equalsIgnoreCase("asc")
        );

        return clientInstitutionService.searchInstitution(name, params);
    }

    @GetMapping(value = "institutions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public InstitutionResponseDTO getInstitutionById(@PathVariable("id") Long id){
        return (clientInstitutionService.getInstitutionById(id));
    }

    @GetMapping(value = "institutions/{institutionId}/boxes/{boxId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BoxShortResponseDTO getBoxById(@PathVariable("institutionId") Long institutionId, @PathVariable("boxId") Long boxId){
        return clientBoxService.getBoxById(institutionId, boxId);
    }

    @PatchMapping(value = "boxes/{boxId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDTO buyBox(@PathVariable("boxId") Long boxId, Authentication authentication){
        return clientBoxService.buyBox(boxId, authentication);
    }

    @GetMapping(value = "me", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserProfileDTO getDataForProfilePage(Authentication authentication){
        return clientService.getDataForProfilePage(authentication);
    }

//    @GetMapping(value = "myOrdersHistory", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<BoxShortResponseDTO> getHistory(Authentication authentication){
//        return clientService.getHistory(authentication);
//    }

    //todo editUser
    //todo deleteUser

}
