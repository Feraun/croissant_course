package com.crois.course.repositories;

import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
import com.crois.course.entity.OrderEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @EntityGraph(value = "Order.withUserAndBox", type = EntityGraph.EntityGraphType.LOAD)
    @Query("select o from OrderEntity o")
    PageResult<OrderEntity> searchOrders(String name, PageParams params);


}
