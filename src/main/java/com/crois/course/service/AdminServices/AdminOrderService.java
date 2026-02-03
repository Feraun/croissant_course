package com.crois.course.service.AdminServices;

import com.crois.course.dto.OrderDTO;
import com.crois.course.mapper.OrderMapper;
import com.crois.course.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminOrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    public OrderDTO getOrderById(Long id){
        return orderMapper.createDtoFromEntity(orderRepository.findById(id).orElseThrow());
    }

    public Long deleteOrderById(Long id){
        orderRepository.deleteById(id);
        return id;
    }

    public Page<OrderDTO> searchOrder(Long id, Pageable pageable){
        return orderRepository.searchOrder(id, pageable).map(orderMapper::createDtoFromEntity);
    }
}
