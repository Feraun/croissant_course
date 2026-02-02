package com.crois.course.service.ManagerServices;

import com.crois.course.dto.InstitutionDTO.InstitutionResponseManagerForGetAll;
import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
import com.crois.course.dto.UserDTO.AuthUser;
import com.crois.course.entity.InstitutionEntity;
import com.crois.course.entity.UserEntity;
import com.crois.course.mapper.InstitutionMapper;
import com.crois.course.repositories.InstitutionRepository;
import com.crois.course.service.SearchService.CriteriaFilter;
import com.crois.course.service.SearchService.CriteriaSearchUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Join;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ManagerInstitutionService {

    @PersistenceContext
    private EntityManager em;

    private final InstitutionMapper institutionMapper;
    private final InstitutionRepository institutionRepository;

    public Page<InstitutionResponseManagerForGetAll> getMyInstitutions(String name,
                                                                       Pageable pageable,
                                                                       Authentication authentication) {

        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        return institutionRepository.searchInstitutionByManager(authUser.getId(), pageable)
                .map(institutionMapper::createManagerDtoFromEntity);

    }

}
