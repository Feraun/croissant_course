package com.crois.course.service.AdminServices;

import com.crois.course.dto.InstitutionDTO.InstitutionRequestDTO;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseDTO;
import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
import com.crois.course.entity.InstitutionEntity;
import com.crois.course.mapper.InstitutionMapper;
import com.crois.course.repositories.CategoryInstitutionRepository;
import com.crois.course.repositories.InstitutionRepository;
import com.crois.course.repositories.UserRepository;
import com.crois.course.service.SearchService.CriteriaFilter;
import com.crois.course.service.SearchService.CriteriaSearchUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminInstitutionService {

    @PersistenceContext
    private EntityManager em;

    private final InstitutionMapper institutionMapper;
    private final InstitutionRepository institutionRepository;

    private final UserRepository userRepository;

    private final CategoryInstitutionRepository categoryInstitutionRepository;

    public PageResult<InstitutionResponseDTO> searchInstitution(String name, PageParams params){

        List<CriteriaFilter<InstitutionEntity>> filters = List.of(
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
                InstitutionEntity.class,
                filters,
                params,
                institutionMapper::createDtoFromEntity
        );
    }

    public InstitutionResponseDTO createInstitution(InstitutionRequestDTO institutionRequestDTO){

        InstitutionEntity institutionEntity = institutionMapper.createEntityFromDTO(institutionRequestDTO);

        institutionEntity.setCategories(categoryInstitutionRepository.findAllById(institutionRequestDTO.categoryIds()));

        institutionEntity.setManagers(userRepository.findAllById(institutionRequestDTO.managersIds()));

        institutionEntity.setCreatedAt(LocalDateTime.now());

        institutionRepository.save(institutionEntity);

        return institutionMapper.createDtoFromEntity(institutionEntity);

    }

    public InstitutionResponseDTO editInstitution(@PathVariable("id") Long id, @RequestBody InstitutionRequestDTO institutionRequestDTO){
        InstitutionEntity institutionEntity = institutionRepository.findById(id).orElseThrow();

        institutionEntity.setName(institutionRequestDTO.name());
        institutionEntity.setCityId(institutionRequestDTO.cityId());
        institutionEntity.setAddress(institutionRequestDTO.address());


        //todo map? надо разбивать список по одному айди и добавлять в список категорий
//        institutionEntity.setCategories(categoryInstitutionRepository.findAllById(institutionRequestDTO.categoryIds()));

        institutionEntity.setContactNumber(institutionRequestDTO.contactNumber());

        //todo map? надо разбивать список по одному айди и добавлять в список менеджеров
//        institutionEntity.setManagers(userRepository.getReferenceById(institutionRequestDTO.managersIds()));

        //todo придумать алгоритм расчета рейтинга на основе отзывов
        institutionEntity.setRating(institutionRequestDTO.rating());

        institutionRepository.save(institutionEntity);

        return(institutionMapper.createDtoFromEntity(institutionEntity));

    }

    public InstitutionResponseDTO getInstitutionById(@PathVariable("id") Long id){
        return(institutionMapper.createDtoFromEntity(institutionRepository.findById(id).orElseThrow()));
    }

    public Long deleteInstitutionById(@PathVariable("id") Long id){
        institutionRepository.deleteById(id);
        return (id);
    }

}
