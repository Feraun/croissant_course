package com.crois.course.service.AdminServices;

import com.crois.course.dto.CategoryInstitutionDTO.CategoryInstitutionDTO;

import com.crois.course.entity.CategoryInstitutionEntity;
import com.crois.course.exceptions.NotFoundException;
import com.crois.course.mapper.CategoryInstitutionMapper;
import com.crois.course.repositories.CategoryInstitutionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminCategoryService {


    private final CategoryInstitutionMapper categoryInstitutionMapper;
    private final CategoryInstitutionRepository categoryInstitutionRepository;

    public CategoryInstitutionDTO createCategoryInstitution(CategoryInstitutionDTO categoryInstitutionDTO){

        CategoryInstitutionEntity categoryInstitutionEntity = categoryInstitutionMapper.createEntityFromDTO(categoryInstitutionDTO);

        categoryInstitutionRepository.save(categoryInstitutionEntity);

        return(categoryInstitutionMapper.createDtoFromEntity(categoryInstitutionEntity));
    }

    @Transactional
    public CategoryInstitutionDTO editCategoryInstitution(Long id, CategoryInstitutionDTO categoryInstitutionDTO){

        categoryInstitutionMapper.updateEntity(categoryInstitutionRepository.getReferenceById(id),
                categoryInstitutionDTO);

        return categoryInstitutionDTO;
    }

    public CategoryInstitutionDTO getByIdCategoryInstitution(Long id){
        CategoryInstitutionEntity categoryInstitutionEntity = categoryInstitutionRepository
                .findById(id)
                .orElseThrow(()->new NotFoundException("Category of Institution not found"));

        return (categoryInstitutionMapper.createDtoFromEntity(categoryInstitutionEntity));
    }

    public Long deleteByIdCategoryInstitution(Long id){
        categoryInstitutionRepository.deleteById(id);
        return (id);
    }

    public Page<CategoryInstitutionDTO> searchCategoryOfInstitution(Long categoryId, String categoryName, Pageable pageable){
        return categoryInstitutionRepository.searchCategories(categoryId, categoryName, pageable)
                .map(categoryInstitutionMapper::createDtoFromEntity);
    }

    public List<CategoryInstitutionDTO> getAllCategory(){
        return categoryInstitutionRepository.findAll().stream().map(categoryInstitutionMapper::createDtoFromEntity).toList();
    }
}
