package com.crois.course.service.ClientServices;

import com.crois.course.dto.BoxDTO.BoxResponseDTO;
import com.crois.course.dto.OrderClientDTO;
import com.crois.course.dto.OrderDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;

import static com.crois.course.Utils.IdUtils.getIdFromAuthentication;

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
    public OrderDTO buyBox(Long boxId, Authentication authentication){

        BoxEntity boxEntity = boxRepository.findById(boxId)
                .orElseThrow(() -> new NotFoundException("Box not found"));

        if (boxEntity.getQuantity() > 0){
            boxEntity.setQuantity(boxEntity.getQuantity() - 1);
        } else {
            throw new NotFoundException("This box sold out");
        }

        UserEntity user = userRepository.getReferenceById(getIdFromAuthentication(authentication));

        OrderEntity orderEntity = orderMapper.createEntity(user, boxEntity, LocalDateTime.now());

        orderRepository.save(orderEntity);

        return orderMapper.createDtoFromEntity(orderEntity);
    }

    public Page<OrderClientDTO> getMyBoxes(Authentication authentication, Pageable pageable){
        return orderRepository.getMyOrders(getIdFromAuthentication(authentication), pageable)
                .map(orderMapper::createClientDTOFromEntity);
    }

}
