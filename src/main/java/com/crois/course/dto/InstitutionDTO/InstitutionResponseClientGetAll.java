package com.crois.course.dto.InstitutionDTO;

import com.crois.course.dto.CategoryInstitutionDTO.ShortCategoryDTO;

import java.util.List;
import java.util.UUID;

public record InstitutionResponseClientGetAll(
        Long id,
        String name,
        String address,
        Double rating,
        List<ShortCategoryDTO> categories,
        UUID logoId
) {
}
