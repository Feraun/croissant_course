package com.crois.course.controller.AdminControllers;

import com.crois.course.dto.OrderDTO;
import com.crois.course.service.AdminServices.AdminOrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/orders")
@AllArgsConstructor
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<OrderDTO> searchOrders(
            @RequestParam(required = false) Long id,
            @PageableDefault Pageable pageable
    ){
        return adminOrderService.searchOrder(id, pageable);
    }

}
