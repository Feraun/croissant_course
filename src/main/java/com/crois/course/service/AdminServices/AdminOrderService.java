package com.crois.course.service.AdminServices;

import com.crois.course.dto.OrderDTO;
import com.crois.course.entity.OrderEntity;
import com.crois.course.mapper.OrderMapper;
import com.crois.course.repositories.OrderRepository;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminOrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    private EntityManager em;

    public OrderDTO getOrderById(Long id){
        return orderMapper.createDtoFromEntity(orderRepository.findById(id).orElseThrow());
    }

    public Long deleteOrderById(Long id){
        orderRepository.deleteById(id);
        return id;
    }

    public Page<OrderDTO> searchOrder(
            Long id,
            int page,
            int size,
            String direction
    ){
    String dir = "desc".equalsIgnoreCase(direction) ? "DESC" : "ASC";

        List<OrderDTO> orderDTOList = em.createQuery(
                "FROM OrderEntity oe " +
                "JOIN FETCH oe.user " +
                "JOIN FETCH oe.box " +
                "WHERE (:id IS NULL OR oe.id = :id) " +
                "ORDER by oe.id " + dir, OrderEntity.class)
                .setParameter("id", id)
                .setFirstResult(page*size)
                .setMaxResults(size)
                .getResultList()
                .stream()
                .map(orderMapper::createDtoFromEntity)
                .toList();

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

        Long total = em.createQuery(
                        "SELECT COUNT(oe) " +
                            "FROM OrderEntity oe " +
                            "WHERE (:id IS NULL OR oe.id = :id)", Long.class)
                .setParameter("id", id)
                .getSingleResult();

        return new PageImpl<>(orderDTOList, pageable, total);
    }

    public List<OrderDTO> getAllOrders(){
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::createDtoFromEntity)
                .toList();
    }
}
