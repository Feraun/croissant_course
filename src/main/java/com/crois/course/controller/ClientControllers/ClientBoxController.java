package com.crois.course.controller.ClientControllers;

import com.crois.course.dto.OrderDTO;
import com.crois.course.service.ClientServices.ClientBoxService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client/boxes")
@AllArgsConstructor
public class ClientBoxController {

    private final ClientBoxService clientBoxService;

    @PatchMapping(value = "/{boxId}")
    public OrderDTO buyBox(@PathVariable("boxId") Long boxId, Authentication authentication){
        return clientBoxService.buyBox(boxId, authentication);
    }

}
