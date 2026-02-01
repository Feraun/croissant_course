package com.crois.course.controller.ManagerControllers;

import com.crois.course.dto.OrderDTO;
import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
import com.crois.course.service.ManagerServices.ManagerOrderService;
import lombok.AllArgsConstructor;
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
    public PageResult<OrderDTO> getAllOrders(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "asc") String direction,

            Authentication authentication
    ){

        PageParams params = new PageParams(
                page,
                size,
                sortBy,
                direction.equalsIgnoreCase("asc")
        );

        return managerOrderService.searchOrders(name, params, authentication);
    }

}
