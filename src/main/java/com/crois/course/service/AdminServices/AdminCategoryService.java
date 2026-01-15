package com.crois.course.service.AdminServices;

import com.crois.course.dto.CategoryInstitutionDTO.CategoryInstitutionDTO;
import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
import com.crois.course.entity.CategoryInstitutionEntity;
import com.crois.course.mapper.CategoryInstitutionMapper;
import com.crois.course.repositories.CategoryInstitutionRepository;
import com.crois.course.service.SearchService.CriteriaFilter;
import com.crois.course.service.SearchService.CriteriaSearchUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminCategoryService {

    @PersistenceContext
    private EntityManager em;

    private final CategoryInstitutionMapper categoryInstitutionMapper;
    private final CategoryInstitutionRepository categoryInstitutionRepository;

    public CategoryInstitutionDTO createCategoryInstitution(@RequestBody CategoryInstitutionDTO categoryInstitutionDTO){

        CategoryInstitutionEntity categoryInstitutionEntity = categoryInstitutionMapper.createEntityFromDTO(categoryInstitutionDTO);

        categoryInstitutionRepository.save(categoryInstitutionEntity);

        return(categoryInstitutionMapper.createDtoFromEntity(categoryInstitutionEntity));
    }

    public CategoryInstitutionDTO editCategoryInstitution(@PathVariable("id") Long id, @RequestBody CategoryInstitutionDTO categoryInstitutionDTO){
        CategoryInstitutionEntity categoryInstitutionEntity = categoryInstitutionRepository.findById(id).orElseThrow();

        categoryInstitutionEntity.setName(categoryInstitutionDTO.name());
        categoryInstitutionEntity.setDescription(categoryInstitutionDTO.description());

        categoryInstitutionRepository.save(categoryInstitutionEntity);

        return (categoryInstitutionMapper.createDtoFromEntity(categoryInstitutionEntity));
    }

    public CategoryInstitutionDTO getByIdCategoryInstitution(@PathVariable("id") Long id){
        return (categoryInstitutionMapper.createDtoFromEntity(categoryInstitutionRepository.findById(id).orElseThrow()));
    }

    public Long deleteByIdCategoryInstitution(@PathVariable("id") Long id){
        categoryInstitutionRepository.deleteById(id);
        return (id);
    }

    public PageResult<CategoryInstitutionDTO> searchCategoryOfInstitution(String name, PageParams params){

        List<CriteriaFilter<CategoryInstitutionEntity>> filters = List.of(
                (cb, root, predicates) -> {
                    if (name != null && !name.isBlank()) {
                        predicates.add(
                                cb.like(
                                        cb.lower(root.get("name")),
                                        "%" + name.toLowerCase() + "%"
                                )
                        );
                    }
                }
        );

        return CriteriaSearchUtil.search(
                em,
                CategoryInstitutionEntity.class,
                filters,
                params,
                categoryInstitutionMapper::createDtoFromEntity
        );
    }

}
