package com.crois.course.dto.BoxDTO;

public record CreateBoxDTO(
        String name,
        String description,
        Double price,
        Boolean randomly,
        Integer quantity
) {
}
