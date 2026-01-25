package com.crois.course.controller;

import com.crois.course.dto.BoxDTO.BoxShortResponseDTO;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseClient;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseDTO;
import com.crois.course.dto.OrderDTO;
import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
import com.crois.course.dto.UserDTO.UserProfileDTO;
import com.crois.course.service.ClientService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client/")
public class ClientController {

    private final ClientService clientService;


    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

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

        return clientService.searchInstitution(name, params);
    }

    @GetMapping(value = "institutions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public InstitutionResponseDTO getInstitutionById(@PathVariable("id") Long id){
        return (clientService.getInstitutionById(id));
    }

    @GetMapping(value = "institutions/{institutionId}/boxes/{boxId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BoxShortResponseDTO getBoxById(@PathVariable("institutionId") Long institutionId, @PathVariable("boxId") Long boxId){
        return clientService.getBoxById(institutionId, boxId);
    }

    @PatchMapping(value = "boxes/{boxId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDTO buyBox(@PathVariable("boxId") Long boxId, Authentication authentication){
        return clientService.buyBox(boxId, authentication);
    }

    @GetMapping(value = "users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserProfileDTO getDataForProfilePage(@PathVariable("id") Long userId){
        return clientService.getDataForProfilePage(userId);
    }

//    @GetMapping(value = "myOrdersHistory", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<BoxShortResponseDTO> getHistory(Authentication authentication){
//        return clientService.getHistory(authentication);
//    }

    //todo editUser
    //todo deleteUser

}
