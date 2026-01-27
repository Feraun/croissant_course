package com.crois.course.service;

import com.crois.course.dto.BoxDTO.BoxShortResponseDTO;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseClient;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseDTO;
import com.crois.course.dto.OrderDTO;
import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
import com.crois.course.dto.UserDTO.AuthUser;
import com.crois.course.dto.UserDTO.UserProfileDTO;
import com.crois.course.entity.*;
import com.crois.course.mapper.BoxMapper;
import com.crois.course.mapper.InstitutionMapper;
import com.crois.course.mapper.OrderMapper;
import com.crois.course.mapper.UserRegistrationMapper;
import com.crois.course.repositories.*;
import com.crois.course.service.SearchService.CriteriaFilter;
import com.crois.course.service.SearchService.CriteriaSearchUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ClientService {

    @PersistenceContext
    private EntityManager em;

    private final InstitutionMapper institutionMapper;
    private final InstitutionRepository institutionRepository;

    private final BoxMapper boxMapper;
    private final BoxRepository boxRepository;

    private final UserRepository userRepository;
    private final UserRegistrationMapper userMapper;

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

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

    public BoxShortResponseDTO getBoxById(@PathVariable("institutionId") Long institutionId, @PathVariable("boxId") Long boxId){
        return boxRepository.findByIdAndInstitutionId(boxId, institutionId)
                .map(boxMapper::createShortDtoFromEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Box not found"));

    }

    @Transactional
    public OrderDTO buyBox(@PathVariable("boxId") Long boxId, Authentication authentication){

        BoxEntity boxEntity = boxRepository.findById(boxId).orElseThrow();

        boxEntity.setQuantity(boxEntity.getQuantity() - 1);

        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        UserEntity user = userRepository.getReferenceById(authUser.getId());

        OrderEntity orderEntity = OrderEntity.builder()
                .box(boxEntity)
                .amount(boxEntity.getPrice())
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();

        orderRepository.save(orderEntity);

        return orderMapper.createDtoFromEntity(orderEntity);
    }

    public UserProfileDTO getDataForProfilePage(Authentication authentication){
        AuthUser authUser = (AuthUser)  authentication.getPrincipal();

        UserEntity user = userRepository.getReferenceById(authUser.getId());

        return userMapper.createUserProfileDtoFromEntity(user);

    }


    //todo доделать историю заказов
    //    public List<BoxShortResponseDTO> getHistory(Authentication authentication){
    //        AuthUser authUser = (AuthUser) authentication.getPrincipal();
    //        return userRepository.findUserBoxHistory(authUser.getId());
    //    }
}
