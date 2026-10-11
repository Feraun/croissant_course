package com.crois.course.service.ManagerServices;

import com.crois.course.dto.OrderDTO;
import com.crois.course.mapper.OrderMapper;
import com.crois.course.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import static com.crois.course.Utils.IdUtils.getIdFromAuthentication;

@Service
@AllArgsConstructor
public class ManagerOrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    public Page<OrderDTO> searchOrders(Long orderId, Authentication authentication, Pageable pageable){
        return orderRepository.searchOrderByManager(orderId, getIdFromAuthentication(authentication), pageable)
                .map(orderMapper::createDtoFromEntity);
    }
}