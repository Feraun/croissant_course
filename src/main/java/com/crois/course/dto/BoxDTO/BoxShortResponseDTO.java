package com.crois.course.dto.BoxDTO;

public record BoxShortResponseDTO(
        Long id,
        String name,
        String description,
        Double price,
        String quantity
) { }
