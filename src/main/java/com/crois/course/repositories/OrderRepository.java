package com.crois.course.repositories;

import com.crois.course.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("""
            SELECT oe FROM OrderEntity oe
            JOIN FETCH oe.user
            JOIN FETCH oe.box
            WHERE oe.id = coalesce(:id, oe.id)
            """)
    Page<OrderEntity> searchOrder(
            @Param("id") Long id,
            Pageable pageable);


    @Query("""
            SELECT oe FROM OrderEntity oe
            JOIN FETCH oe.box b
            JOIN FETCH oe.user u
            JOIN b.institution i
            WHERE EXISTS (
                SELECT 1 FROM i.managers m
                WHERE m.id = :managerId
            )
            AND (oe.id = coalesce(:id, oe.id))
            """)
    Page<OrderEntity> searchOrderByManager(
            @Param("orderId") Long orderId,
            @Param("managerId") Long managerId,
            Pageable pageable
    );
}