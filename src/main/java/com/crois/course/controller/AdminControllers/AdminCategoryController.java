package com.crois.course.controller.AdminControllers;


import com.crois.course.dto.CategoryInstitutionDTO.CategoryInstitutionDTO;
import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
import com.crois.course.service.AdminServices.AdminCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/categoriesOfInstitution")
@AllArgsConstructor
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;

    @GetMapping()
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

    @PostMapping(value = "/createCategory", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryInstitutionDTO createCategoryInstitution(@RequestBody CategoryInstitutionDTO categoryInstitutionDTO){
        return (adminCategoryService.createCategoryInstitution(categoryInstitutionDTO));
    }

    @PatchMapping(value = "/editCategory/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryInstitutionDTO editCategoryInstitution(@PathVariable("id") Long id, @RequestBody CategoryInstitutionDTO categoryInstitutionDTO){
        return (adminCategoryService.editCategoryInstitution(id, categoryInstitutionDTO));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryInstitutionDTO getCategoryInstitutionById(@PathVariable("id") Long id){
        return (adminCategoryService.getByIdCategoryInstitution(id));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteCategoryInstitution(@PathVariable("id") Long id){
        return (adminCategoryService.deleteByIdCategoryInstitution(id));
    }

    @GetMapping("/getAll")
    public List<CategoryInstitutionDTO> getAllCategories(){
        return adminCategoryService.getAllCategory();
    }

}
