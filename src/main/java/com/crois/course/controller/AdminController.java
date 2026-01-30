package com.crois.course.controller;


import com.crois.course.dto.BoxDTO.BoxShortResponseDTO;
import com.crois.course.dto.BoxDTO.CreateBoxDTO;
import com.crois.course.dto.CategoryInstitutionDTO.CategoryInstitutionDTO;
import com.crois.course.dto.CityDTO.CityDTO;
import com.crois.course.dto.InstitutionDTO.InstitutionRequestDTO;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseDTO;
import com.crois.course.dto.OrderDTO;
import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
import com.crois.course.entity.CityEntity;
import com.crois.course.entity.OrderEntity;
import com.crois.course.mapper.CityMapper;
import com.crois.course.mapper.OrderMapper;
import com.crois.course.service.AdminServices.*;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import jakarta.persistence.metamodel.Metamodel;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpHeaders.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/api/admin/")
@AllArgsConstructor
public class AdminController {

    private final AdminCityService adminCityService;
    private final AdminCategoryService adminCategoryService;
    private final AdminInstitutionService adminInstitutionService;
    private final AdminBoxService adminBoxService;


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

        return adminCityService.searchCity(name, params);
    }

    @PostMapping(value = "cities/createCity", produces = MediaType.APPLICATION_JSON_VALUE)
    public CityDTO createCity(@RequestBody CityDTO cityDTO){
        return (adminCityService.createCity(cityDTO));
    }

    @PatchMapping(value = "cities/editCity/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CityDTO editCity(@PathVariable("id") Long id, @RequestBody CityDTO cityDTO){
        return (adminCityService.editCity(id, cityDTO));
    }

    @GetMapping(value = "cities/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CityDTO getCityById(@PathVariable("id") Long id){
        return (adminCityService.getCityById(id));
    }

    @DeleteMapping(value = "cities/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteCityById(@PathVariable("id") Long id){
        return (adminCityService.deleteCityById(id));
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

        return adminCategoryService.searchCategoryOfInstitution(name, params);
    }

    @PostMapping(value = "categoriesOfInstitution/createCategory", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryInstitutionDTO createCategoryInstitution(@RequestBody CategoryInstitutionDTO categoryInstitutionDTO){
        return (adminCategoryService.createCategoryInstitution(categoryInstitutionDTO));
    }

    @PatchMapping(value = "categoriesOfInstitution/editCategory/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryInstitutionDTO editCategoryInstitution(@PathVariable("id") Long id, @RequestBody CategoryInstitutionDTO categoryInstitutionDTO){
        return (adminCategoryService.editCategoryInstitution(id, categoryInstitutionDTO));
    }

    @GetMapping(value = "categoriesOfInstitution/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryInstitutionDTO editCategoryInstitution(@PathVariable("id") Long id){
        return (adminCategoryService.getByIdCategoryInstitution(id));
    }

    @DeleteMapping(value = "categoriesOfInstitution/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteCategoryInstitution(@PathVariable("id") Long id){
        return (adminCategoryService.deleteByIdCategoryInstitution(id));
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

        return adminInstitutionService.searchInstitution(name, params);
    }

    @PostMapping(value = "institutions/createInstitution", produces = MediaType.APPLICATION_JSON_VALUE)
    public InstitutionResponseDTO createInstitution(@ModelAttribute InstitutionRequestDTO InstitutionRequestDTO, @RequestParam("file") MultipartFile file) throws Exception {
        return (adminInstitutionService.createInstitution(InstitutionRequestDTO, file));
    }

    @PatchMapping(value = "institutions/editInstitution/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public InstitutionResponseDTO editInstitution(@PathVariable("id") Long id, @RequestBody InstitutionRequestDTO institutionRequestDTO){
        return (adminInstitutionService.editInstitution(id, institutionRequestDTO));
    }

    @GetMapping(value = "institutions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public InstitutionResponseDTO getInstitutionById(@PathVariable("id") Long id){
        return (adminInstitutionService.getInstitutionById(id));
    }

    @DeleteMapping(value = "institutions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteInstitutionById(@PathVariable("id") Long id){
        return (adminInstitutionService.deleteInstitutionById(id));
    }

    @PostMapping(value = "institutions/{institutionId}/createBox", produces = MediaType.APPLICATION_JSON_VALUE)
    public CreateBoxDTO createBox(@PathVariable("institutionId") Long institutionId, @RequestBody CreateBoxDTO createBoxDTO){
        return adminBoxService.createBox(institutionId, createBoxDTO);
    }

    @PatchMapping(value = "institutions/{institutionId}/editBox/{boxId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CreateBoxDTO editBox(@PathVariable("institutionId") Long institutionId, @PathVariable("boxId") Long boxId, @RequestBody CreateBoxDTO createBoxDTO){
        return adminBoxService.editBox(institutionId, boxId, createBoxDTO);
    }

    @GetMapping(value = "institutions/{institutionId}/boxes/{boxId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BoxShortResponseDTO getBoxById(@PathVariable("institutionId") Long institutionId, @PathVariable("boxId") Long boxId){
        return adminBoxService.getBoxById(institutionId, boxId);
    }

    @DeleteMapping(value = "institutions/{institutionId}/boxes/{boxId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteBoxById(@PathVariable("institutionId") Long institutionId, @PathVariable("boxId") Long boxId){
        return adminBoxService.deleteBoxById(institutionId, boxId);
    }

//    @GetMapping(value = "orders", produces = MediaType.APPLICATION_JSON_VALUE)
//    public PageResult<OrderDTO> getAllOrders(
//            @RequestParam(required = false) String name,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(required = false) String sortBy,
//            @RequestParam(defaultValue = "asc") String direction
//    ) {
//        PageParams params = new PageParams(
//                page,
//                size,
//                sortBy,
//                direction.equalsIgnoreCase("asc")
//        );
//
//        return adminBoxService.searchOrders(name, params);
//    }

    private final AdminOrderService adminOrderService;

    @GetMapping(value = "orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<OrderDTO> searchOrders(
            @RequestParam(required = false) Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "asc") String direction
    ){
        return adminOrderService.searchOrder(id, page, size, direction);
    }
}
