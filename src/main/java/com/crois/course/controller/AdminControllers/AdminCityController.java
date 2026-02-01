package com.crois.course.controller.AdminControllers;

import com.crois.course.dto.CityDTO.CityDTO;
import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
import com.crois.course.service.AdminServices.AdminCityService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/cities")
@AllArgsConstructor
public class AdminCityController {

    private final AdminCityService adminCityService;

    @GetMapping()
    public PageResult<CityDTO> searchCity(
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

    @PostMapping(value = "/createCity", produces = MediaType.APPLICATION_JSON_VALUE)
    public CityDTO createCity(@RequestBody CityDTO cityDTO){
        return (adminCityService.createCity(cityDTO));
    }

    @PatchMapping(value = "/editCity/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CityDTO editCity(@PathVariable("id") Long id, @RequestBody CityDTO cityDTO){
        return (adminCityService.editCity(id, cityDTO));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CityDTO getCityById(@PathVariable("id") Long id){
        return (adminCityService.getCityById(id));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteCityById(@PathVariable("id") Long id){
        return (adminCityService.deleteCityById(id));
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CityDTO> getAllCities(){
        return adminCityService.getAllCities();
    }

}
