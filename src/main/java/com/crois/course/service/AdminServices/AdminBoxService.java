package com.crois.course.service.AdminServices;

import com.crois.course.dto.BoxDTO.BoxShortResponseDTO;
import com.crois.course.dto.BoxDTO.CreateBoxDTO;
import com.crois.course.dto.OrderDTO;
import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
import com.crois.course.entity.*;
import com.crois.course.mapper.BoxMapper;
import com.crois.course.mapper.OrderMapper;
import com.crois.course.repositories.BoxRepository;
import com.crois.course.repositories.InstitutionRepository;
import com.crois.course.repositories.OrderRepository;
import com.crois.course.service.SearchService.CriteriaFilter;
import com.crois.course.service.SearchService.CriteriaSearchUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminBoxService {

    @PersistenceContext
    private EntityManager em;

    private final BoxMapper boxMapper;
    private final BoxRepository boxRepository;

    private final InstitutionRepository institutionRepository;

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public CreateBoxDTO createBox(Long institutionId, CreateBoxDTO createBoxDTO){
        BoxEntity boxEntity = boxMapper.createEntityFromDtoForNewBox(createBoxDTO);

        boxEntity.setInstitution(institutionRepository.getReferenceById(institutionId));

        boxRepository.save(boxEntity);

        return boxMapper.createDtoFromEntity(boxEntity);

    }

    public CreateBoxDTO editBox(Long institutionId, Long boxId, CreateBoxDTO createBoxDTO){
        BoxEntity boxEntity = boxRepository.findById(boxId).orElseThrow();

        boxEntity.setName(createBoxDTO.name());
        boxEntity.setDescription(createBoxDTO.description());
        boxEntity.setPrice(createBoxDTO.price());

        boxRepository.save(boxEntity);

        return boxMapper.createDtoFromEntity(boxEntity);
    }

    public BoxShortResponseDTO getBoxById(Long institutionId, Long boxId){
        return boxRepository.findByIdAndInstitutionId(boxId, institutionId)
                .map(boxMapper::createShortDtoFromEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Box not found"));

    }

    public Long deleteBoxById(Long institutionId, Long boxId){

        if (boxRepository.existsByIdAndInstitutionId(boxId, institutionId)){
            boxRepository.deleteById(boxId);
            return boxId;
        }
        else{
            throw new RuntimeException("Бокса нет");
        }
    }

    public PageResult<OrderDTO> searchOrders(String name, PageParams params){


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
