package com.crois.course.controller;


import com.crois.course.dto.CityDTO.CityDTO;
import com.crois.course.service.AdminService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService){
        this.adminService = adminService;
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
}
