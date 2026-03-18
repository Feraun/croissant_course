package com.crois.course.dto.InstitutionDTO;

import com.crois.course.dto.BoxDTO.BoxResponseDTO;
import com.crois.course.dto.CategoryInstitutionDTO.CategoryInstitutionDTO;

import java.util.List;
import java.util.UUID;

public record InstitutionResponseClient(
        Long id,
        String name,
        String address,
        Double rating,
        String contactNumber,
        List<CategoryInstitutionDTO> categories,
        List<BoxResponseDTO> boxes,
        UUID logoId
) {}
