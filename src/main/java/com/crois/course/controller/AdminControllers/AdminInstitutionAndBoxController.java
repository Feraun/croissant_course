package com.crois.course.controller.AdminControllers;

import com.crois.course.dto.BoxDTO.BoxShortResponseDTO;
import com.crois.course.dto.BoxDTO.CreateBoxDTO;
import com.crois.course.dto.InstitutionDTO.InstitutionRequestDTO;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseClient;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseDTO;
import com.crois.course.service.AdminServices.AdminBoxService;
import com.crois.course.service.AdminServices.AdminInstitutionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/institutions")
@AllArgsConstructor
public class AdminInstitutionAndBoxController {

    private final AdminInstitutionService adminInstitutionService;
    private final AdminBoxService adminBoxService;

    @GetMapping()
    public Page<InstitutionResponseDTO> searchInstitutions(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String contactNumber,
            @RequestParam(required = false) Long id,
            @PageableDefault Pageable pageable
    ) {
        return adminInstitutionService.searchInstitution(name, address, city, contactNumber, id, pageable);
    }

    @PostMapping(value = "/createInstitution", produces = MediaType.APPLICATION_JSON_VALUE)
    public InstitutionResponseDTO createInstitution(@ModelAttribute InstitutionRequestDTO InstitutionRequestDTO, @RequestParam("file") MultipartFile file) throws Exception {
        return (adminInstitutionService.createInstitution(InstitutionRequestDTO, file));
    }

    @PatchMapping(value = "/editInstitution/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public InstitutionResponseDTO editInstitution(@PathVariable("id") Long id, @RequestBody InstitutionRequestDTO institutionRequestDTO){
        return (adminInstitutionService.editInstitution(id, institutionRequestDTO));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public InstitutionResponseDTO getInstitutionById(@PathVariable("id") Long id){
        return (adminInstitutionService.getInstitutionById(id));
    }


    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteInstitutionById(@PathVariable("id") Long id){
        return (adminInstitutionService.deleteInstitutionById(id));
    }

    @PostMapping(value = "/{institutionId}/createBox", produces = MediaType.APPLICATION_JSON_VALUE)
    public CreateBoxDTO createBox(@PathVariable("institutionId") Long institutionId, @RequestBody CreateBoxDTO createBoxDTO){
        return adminBoxService.createBox(institutionId, createBoxDTO);
    }

    @PatchMapping(value = "/{institutionId}/editBox/{boxId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CreateBoxDTO editBox(@PathVariable("institutionId") Long institutionId, @PathVariable("boxId") Long boxId, @RequestBody CreateBoxDTO createBoxDTO){
        return adminBoxService.editBox(institutionId, boxId, createBoxDTO);
    }

    @GetMapping(value = "/{institutionId}/boxes/{boxId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BoxShortResponseDTO getBoxById(@PathVariable("institutionId") Long institutionId, @PathVariable("boxId") Long boxId){
        return adminBoxService.getBoxById(institutionId, boxId);
    }

    @DeleteMapping(value = "/{institutionId}/boxes/{boxId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteBoxById(@PathVariable("institutionId") Long institutionId, @PathVariable("boxId") Long boxId){
        return adminBoxService.deleteBoxById(institutionId, boxId);
    }

}
