package com.crois.course.dto.InstitutionDTO;

import java.util.List;

public record InstitutionRequestDTO(
        String name,
        Long cityId,
        String address,
        Double rating,
        String contactNumber,
        List<Long> categoryIds,
        List<Long> managersIds
) { }
