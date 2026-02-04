package com.crois.course.dto.InstitutionDTO;

import com.crois.course.dto.CategoryInstitutionDTO.ShortCategoryDTO;

import java.util.Date;
import java.util.List;

public record InstitutionResponseDTO(
        Long id,
        String name,
        Long cityId,
        String address,
        Double rating,
        String contactNumber,
        Date createdAt,
        List<ShortCategoryDTO> categories,
        String logoImage
) { }
