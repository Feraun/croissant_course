package com.crois.course.dto.InstitutionDTO;


import com.crois.course.dto.CategoryInstitutionDTO.CategoryInstitutionDTO;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record InstitutionResponseDTO(
        Long id,
        String name,
        String cityName,
        String address,
        Double rating,
        String contactNumber,
        Date createdAt,
        List<CategoryInstitutionDTO> categories,
        UUID logoId
) { }
