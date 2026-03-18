package com.crois.course.controller.ClientControllers;

import com.crois.course.dto.OrderClientDTO;
import com.crois.course.dto.OrderDTO;
import com.crois.course.service.ClientServices.ClientBoxService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client/orders")
@AllArgsConstructor
public class ClientOrderController {

    private final ClientBoxService clientBoxService;

    @GetMapping()
    public Page<OrderClientDTO> getMyOrders(Authentication authentication, Pageable pageable){
        return clientBoxService.getMyBoxes(authentication, pageable);
    }

}
