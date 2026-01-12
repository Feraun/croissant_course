package com.crois.course.dto.InstitutionDTO;

import com.crois.course.dto.BoxDTO.BoxShortResponseDTO;
import com.crois.course.dto.CategoryInstitutionDTO.CategoryInstitutionDTO;

import java.util.List;

public record InstitutionResponseClient(
        Long id,
        String name,
        String address,
        Double rating,
        String contactNumber,
        List<CategoryInstitutionDTO> categories
) {}
