package com.crois.course.controller.ClientControllers;

import com.crois.course.dto.BoxDTO.BoxShortResponseDTO;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseClient;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseDTO;
import com.crois.course.service.ClientServices.ClientBoxService;
import com.crois.course.service.ClientServices.ClientInstitutionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client/institutions")
@AllArgsConstructor
public class ClientInstitutionController {

    private final ClientInstitutionService clientInstitutionService;
    private final ClientBoxService clientBoxService;

    @GetMapping()
    public Page<InstitutionResponseClient> searchInstitutions(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String contactNumber,
            @RequestParam(required = false) Long id,
            @PageableDefault Pageable pageable
    ) {
        return clientInstitutionService.searchInstitution(name, address, city, contactNumber, id, pageable);
    }

    @GetMapping(value = "/{id}")
    public InstitutionResponseDTO getInstitutionById(@PathVariable("id") Long id){
        return (clientInstitutionService.getInstitutionById(id));
    }

    @GetMapping(value = "/{institutionId}/boxes/{boxId}")
    public BoxShortResponseDTO getBoxById(@PathVariable("institutionId") Long institutionId, @PathVariable("boxId") Long boxId){
        return clientBoxService.getBoxById(institutionId, boxId);
    }

}
