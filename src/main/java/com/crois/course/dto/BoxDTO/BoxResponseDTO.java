package com.crois.course.dto.BoxDTO;

public record BoxResponseDTO(
        Long id,
        String name,
        String description,
        Double price,
        Integer quantity
) { }
