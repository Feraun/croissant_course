package com.crois.course.service.ManagerServices;

import com.crois.course.dto.OrderDTO;
import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
import com.crois.course.dto.UserDTO.AuthUser;
import com.crois.course.entity.BoxEntity;
import com.crois.course.entity.InstitutionEntity;
import com.crois.course.entity.OrderEntity;
import com.crois.course.entity.UserEntity;
import com.crois.course.mapper.OrderMapper;
import com.crois.course.service.SearchService.CriteriaFilter;
import com.crois.course.service.SearchService.CriteriaSearchUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ManagerOrderService {

    @PersistenceContext
    private EntityManager em;

    private final OrderMapper orderMapper;

    public PageResult<OrderDTO> searchOrders(String name, PageParams params, Authentication authentication){

        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        List<CriteriaFilter<OrderEntity>> filters = List.of(
                (cb, root, predicates) -> {
                    if (name != null && !name.isBlank()) {
                        predicates.add(
                                cb.like(
                                        cb.lower(root.get("name")),
                                        "%" + name.toLowerCase() + "%"
                                )
                        );
                    }

                    Join<OrderEntity, BoxEntity> boxJoin =
                            root.join("box", JoinType.INNER);

                    Join<BoxEntity, InstitutionEntity> institutionJoin =
                            boxJoin.join("institution", JoinType.INNER);

                    Join<InstitutionEntity, UserEntity> managerJoin =
                            institutionJoin.join("managers", JoinType.INNER);


                    predicates.add(cb.equal(managerJoin.get("id"), authUser.getId()));
                }
        );

        return CriteriaSearchUtil.search(
                em,
                OrderEntity.class,
                filters,
                params,
                orderMapper::createDtoFromEntity
        );
    }
}