package com.crois.course.service.ManagerServices;

import com.crois.course.dto.InstitutionDTO.InstitutionResponseManagerForGetAll;
import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
import com.crois.course.dto.UserDTO.AuthUser;
import com.crois.course.entity.InstitutionEntity;
import com.crois.course.entity.UserEntity;
import com.crois.course.mapper.InstitutionMapper;
import com.crois.course.service.SearchService.CriteriaFilter;
import com.crois.course.service.SearchService.CriteriaSearchUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Join;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ManagerInstitutionService {

    @PersistenceContext
    private EntityManager em;

    private final InstitutionMapper institutionMapper;

    public PageResult<InstitutionResponseManagerForGetAll> getMyInstitutions(String name, PageParams params, Authentication authentication){

        AuthUser authUser = (AuthUser) authentication.getPrincipal();

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

                    //фильтр на то в какое заведение входит менеджер и тогда он будет видеть только свои заведения
                    Join<InstitutionEntity, UserEntity> managerJoin = root.join("managers");

                    predicates.add(cb.equal(managerJoin.get("id"), authUser.getId()));
                }
        );

        return CriteriaSearchUtil.search(
                em,
                InstitutionEntity.class,
                filters,
                params,
                institutionMapper::createManagerDtoFromEntity
        );
    }

}
