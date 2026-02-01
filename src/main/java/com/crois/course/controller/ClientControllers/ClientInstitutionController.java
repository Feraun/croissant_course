package com.crois.course.controller.ClientControllers;

import com.crois.course.dto.BoxDTO.BoxShortResponseDTO;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseClient;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseDTO;
import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
import com.crois.course.service.ClientServices.ClientBoxService;
import com.crois.course.service.ClientServices.ClientInstitutionService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client/institutions")
@AllArgsConstructor
public class ClientInstitutionController {

    private final ClientInstitutionService clientInstitutionService;
    private final ClientBoxService clientBoxService;

    @GetMapping()
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

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public InstitutionResponseDTO getInstitutionById(@PathVariable("id") Long id){
        return (clientInstitutionService.getInstitutionById(id));
    }

    @GetMapping(value = "/{institutionId}/boxes/{boxId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BoxShortResponseDTO getBoxById(@PathVariable("institutionId") Long institutionId, @PathVariable("boxId") Long boxId){
        return clientBoxService.getBoxById(institutionId, boxId);
    }

}
