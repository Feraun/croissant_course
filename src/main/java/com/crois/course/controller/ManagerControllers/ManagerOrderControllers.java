package com.crois.course.controller.ManagerControllers;

import com.crois.course.dto.OrderDTO;
import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
import com.crois.course.service.ManagerServices.ManagerOrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/manager/orders")
@AllArgsConstructor
public class ManagerOrderControllers {

    private final ManagerOrderService managerOrderService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<OrderDTO> getAllOrders(
            @RequestParam(required = false) Long orderId,
            @PageableDefault Pageable pageable,
            Authentication authentication
    ){
        return managerOrderService.searchOrders(orderId, authentication, pageable);
    }

}
