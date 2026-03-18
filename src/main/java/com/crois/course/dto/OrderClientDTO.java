package com.crois.course.dto;

import com.crois.course.dto.BoxDTO.BoxOrderDTO;

public record OrderClientDTO(
        Long id,
        BoxOrderDTO box,
        String createdAt
) {}

