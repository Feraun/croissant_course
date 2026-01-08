package com.crois.course.dto.BoxDTO;

public record RandomBoxResponseDTO(
        String name,
        String description,
        Double price,
        String boxStatus
) { }
