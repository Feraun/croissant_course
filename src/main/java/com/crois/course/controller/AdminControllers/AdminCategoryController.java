package com.crois.course.controller.AdminControllers;


import com.crois.course.dto.CategoryInstitutionDTO.CategoryInstitutionDTO;
import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
import com.crois.course.service.AdminServices.AdminCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/categoriesOfInstitution")
@AllArgsConstructor
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;

    @GetMapping()
    public Page<CategoryInstitutionDTO> searchCategoriesInstitution(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId,
            @PageableDefault Pageable pageable
            ) {
        return adminCategoryService.searchCategoryOfInstitution(categoryId, name, pageable);
    }

    @PostMapping(value = "/createCategory")
    public CategoryInstitutionDTO createCategoryInstitution(@RequestBody CategoryInstitutionDTO categoryInstitutionDTO){
        return (adminCategoryService.createCategoryInstitution(categoryInstitutionDTO));
    }

    @PatchMapping(value = "/editCategory/{id}")
    public CategoryInstitutionDTO editCategoryInstitution(@PathVariable("id") Long id, @RequestBody CategoryInstitutionDTO categoryInstitutionDTO){
        return (adminCategoryService.editCategoryInstitution(id, categoryInstitutionDTO));
    }

    @GetMapping(value = "/{id}")
    public CategoryInstitutionDTO getCategoryInstitutionById(@PathVariable("id") Long id){
        return (adminCategoryService.getByIdCategoryInstitution(id));
    }

    @DeleteMapping(value = "/{id}")
    public Long deleteCategoryInstitution(@PathVariable("id") Long id){
        return (adminCategoryService.deleteByIdCategoryInstitution(id));
    }

    @GetMapping("/getAll")
    public List<CategoryInstitutionDTO> getAllCategories(){
        return adminCategoryService.getAllCategory();
    }

}
