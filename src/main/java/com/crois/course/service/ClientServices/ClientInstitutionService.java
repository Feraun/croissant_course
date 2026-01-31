package com.crois.course.service.ClientServices;

import com.crois.course.dto.InstitutionDTO.InstitutionResponseClient;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseDTO;
import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
import com.crois.course.entity.InstitutionEntity;
import com.crois.course.mapper.InstitutionMapper;
import com.crois.course.repositories.InstitutionRepository;
import com.crois.course.service.SearchService.CriteriaFilter;
import com.crois.course.service.SearchService.CriteriaSearchUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@AllArgsConstructor
public class ClientInstitutionService {

    private final InstitutionMapper institutionMapper;
    private final InstitutionRepository institutionRepository;

    @PersistenceContext
    private EntityManager em;
    public PageResult<InstitutionResponseClient> searchInstitution(String name, PageParams params){

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
                institutionMapper::createDtoForClient
        );
    }

    public InstitutionResponseDTO getInstitutionById(@PathVariable("id") Long id){
        return(institutionMapper.createDtoFromEntity(institutionRepository.findById(id).orElseThrow()));
    }

}
