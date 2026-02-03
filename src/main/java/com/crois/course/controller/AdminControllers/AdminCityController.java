package com.crois.course.controller.AdminControllers;

import com.crois.course.dto.CategoryInstitutionDTO.CategoryInstitutionDTO;
import com.crois.course.dto.CityDTO.CityDTO;
import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
import com.crois.course.service.AdminServices.AdminCityService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/cities")
@AllArgsConstructor
public class AdminCityController {

    private final AdminCityService adminCityService;

    @GetMapping()
    public Page<CityDTO> searchCategoriesInstitution(
            @RequestParam(required = false) String cityName,
            @RequestParam(required = false) Long cityId,
            @PageableDefault Pageable pageable
    ) {
        return adminCityService.searchCity(cityName, cityId, pageable);
    }

    @PostMapping(value = "/createCity")
    public CityDTO createCity(@RequestBody CityDTO cityDTO){
        return (adminCityService.createCity(cityDTO));
    }

    @PatchMapping(value = "/editCity/{id}")
    public CityDTO editCity(@PathVariable("id") Long id, @RequestBody CityDTO cityDTO){
        return (adminCityService.editCity(id, cityDTO));
    }

    @GetMapping(value = "/{id}")
    public CityDTO getCityById(@PathVariable("id") Long id){
        return (adminCityService.getCityById(id));
    }

    @DeleteMapping(value = "/{id}")
    public Long deleteCityById(@PathVariable("id") Long id){
        return (adminCityService.deleteCityById(id));
    }

    @GetMapping(value = "/getAll")
    public List<CityDTO> getAllCities(){
        return adminCityService.getAllCities();
    }

}
