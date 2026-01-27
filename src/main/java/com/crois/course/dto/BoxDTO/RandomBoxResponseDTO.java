package com.crois.course.dto.BoxDTO;

public record RandomBoxResponseDTO(
        Long id,
        String name,
        String description,
        Double price,
        Integer quantity
) { }
