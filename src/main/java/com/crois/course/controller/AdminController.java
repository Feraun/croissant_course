package com.crois.course.controller;


import com.crois.course.dto.BoxDTO.BoxShortResponseDTO;
import com.crois.course.dto.BoxDTO.CreateBoxDTO;
import com.crois.course.dto.CategoryInstitutionDTO.CategoryInstitutionDTO;
import com.crois.course.dto.CityDTO.CityDTO;
import com.crois.course.dto.InstitutionDTO.InstitutionRequestDTO;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseDTO;
import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
import com.crois.course.service.AdminService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @GetMapping("/cities")
    public PageResult<CityDTO> getAllCity(
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

        return adminService.searchCity(name, params);
    }

    @PostMapping(value = "cities/createCity", produces = MediaType.APPLICATION_JSON_VALUE)
    public CityDTO createCity(@RequestBody CityDTO cityDTO){
        return (adminService.createCity(cityDTO));
    }

    @PatchMapping(value = "cities/editCity/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CityDTO editCity(@PathVariable("id") Long id, @RequestBody CityDTO cityDTO){
        return (adminService.editCity(id, cityDTO));
    }

    @GetMapping(value = "cities/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CityDTO getCityById(@PathVariable("id") Long id){
        return (adminService.getCityById(id));
    }

    @DeleteMapping(value = "cities/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteCityById(@PathVariable("id") Long id){
        return (adminService.deleteCityById(id));
    }


    @GetMapping("/categoriesOfInstitution")
    public PageResult<CategoryInstitutionDTO> searchCategoriesInstitution(
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

        return adminService.searchCategoryOfInstitution(name, params);
    }

    @PostMapping(value = "categoriesOfInstitution/createCategory", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryInstitutionDTO createCategoryInstitution(@RequestBody CategoryInstitutionDTO categoryInstitutionDTO){
        return (adminService.createCategoryInstitution(categoryInstitutionDTO));
    }

    @PatchMapping(value = "categoriesOfInstitution/editCategory/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryInstitutionDTO editCategoryInstitution(@PathVariable("id") Long id, @RequestBody CategoryInstitutionDTO categoryInstitutionDTO){
        return (adminService.editCategoryInstitution(id, categoryInstitutionDTO));
    }

    @GetMapping(value = "categoriesOfInstitution/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryInstitutionDTO editCategoryInstitution(@PathVariable("id") Long id){
        return (adminService.getByIdCategoryInstitution(id));
    }

    @DeleteMapping(value = "categoriesOfInstitution/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteCategoryInstitution(@PathVariable("id") Long id){
        return (adminService.deleteByIdCategoryInstitution(id));
    }

    @GetMapping("/institutions")
    public PageResult<InstitutionResponseDTO> searchInstitutions(
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

        return adminService.searchInstitution(name, params);
    }

    @PostMapping(value = "institutions/createInstitution", produces = MediaType.APPLICATION_JSON_VALUE)
    public InstitutionResponseDTO createInstitution(@RequestBody InstitutionRequestDTO InstitutionRequestDTO){
        return (adminService.createInstitution(InstitutionRequestDTO));
    }

    @PatchMapping(value = "institutions/editInstitution/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public InstitutionResponseDTO editInstitution(@PathVariable("id") Long id, @RequestBody InstitutionRequestDTO institutionRequestDTO){
        return (adminService.editInstitution(id, institutionRequestDTO));
    }

    @GetMapping(value = "institutions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public InstitutionResponseDTO getInstitutionById(@PathVariable("id") Long id){
        return (adminService.getInstitutionById(id));
    }

    @DeleteMapping(value = "institutions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteInstitutionById(@PathVariable("id") Long id){
        return (adminService.deleteInstitutionById(id));
    }

    @PostMapping(value = "institutions/{institutionId}/createBox", produces = MediaType.APPLICATION_JSON_VALUE)
    public CreateBoxDTO createBox(@PathVariable("institutionId") Long institutionId, @RequestBody CreateBoxDTO createBoxDTO){
        return adminService.createBox(institutionId, createBoxDTO);
    }

    @PatchMapping(value = "institutions/{institutionId}/editBox/{boxId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CreateBoxDTO editBox(@PathVariable("institutionId") Long institutionId, @PathVariable("boxId") Long boxId, @RequestBody CreateBoxDTO createBoxDTO){
        return adminService.editBox(institutionId, boxId, createBoxDTO);
    }

    @GetMapping(value = "institutions/{institutionId}/boxes/{boxId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BoxShortResponseDTO getBoxById(@PathVariable("institutionId") Long institutionId, @PathVariable("boxId") Long boxId){
        return adminService.getBoxById(institutionId, boxId);
    }

    @DeleteMapping(value = "institutions/{institutionId}/boxes/{boxId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteBoxById(@PathVariable("institutionId") Long institutionId, @PathVariable("boxId") Long boxId){
        return adminService.deleteBoxById(institutionId, boxId);
    }
}
