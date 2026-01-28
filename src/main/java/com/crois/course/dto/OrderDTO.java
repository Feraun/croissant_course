package com.crois.course.dto;

import com.crois.course.dto.BoxDTO.BoxOrderDTO;
import com.crois.course.dto.UserDTO.UserOrderDTO;

public record OrderDTO(
        Long id,
        UserOrderDTO user,
        BoxOrderDTO box,
        String createdAt
) { }