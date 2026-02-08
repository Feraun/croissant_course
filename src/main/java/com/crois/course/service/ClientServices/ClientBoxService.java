package com.crois.course.service.ClientServices;

import com.crois.course.dto.BoxDTO.BoxResponseDTO;
import com.crois.course.dto.OrderDTO;
import com.crois.course.dto.UserDTO.AuthUser;
import com.crois.course.entity.BoxEntity;
import com.crois.course.entity.OrderEntity;
import com.crois.course.entity.UserEntity;
import com.crois.course.exceptions.NotFoundException;
import com.crois.course.mapper.BoxMapper;
import com.crois.course.mapper.OrderMapper;
import com.crois.course.repositories.BoxRepository;
import com.crois.course.repositories.OrderRepository;
import com.crois.course.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ClientBoxService {

    private final BoxMapper boxMapper;
    private final BoxRepository boxRepository;

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public BoxResponseDTO getBoxById(@PathVariable("institutionId") Long institutionId, @PathVariable("boxId") Long boxId){
        return boxRepository.findByIdAndInstitutionId(boxId, institutionId)
                .map(boxMapper::createShortDtoFromEntity)
                .orElseThrow(()->new NotFoundException("Box not found"));

    }

    @Transactional
    public OrderDTO buyBox(@PathVariable("boxId") Long boxId, Authentication authentication){

        BoxEntity boxEntity = boxRepository.findById(boxId).orElseThrow(()->new NotFoundException("Box not found"));

        boxEntity.setQuantity(boxEntity.getQuantity() - 1);

        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        UserEntity user = userRepository.getReferenceById(authUser.getId());

        OrderEntity orderEntity = orderMapper.createEntity(user, boxEntity, LocalDateTime.now());

        orderRepository.save(orderEntity);

        return orderMapper.createDtoFromEntity(orderEntity);
    }

}
