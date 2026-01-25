package com.crois.course.dto;

public record OrderDTO(
        Long id,
        Long userId,
        Long boxId,
        String createdAt
) {
}
