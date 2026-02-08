package com.crois.course.dto.InstitutionDTO;


import com.crois.course.dto.CategoryInstitutionDTO.CategoryInstitutionDTO;

import java.util.Date;
import java.util.List;

public record InstitutionResponseDTO(
        Long id,
        String name,
        String cityName,
        String address,
        Double rating,
        String contactNumber,
        Date createdAt,
        List<CategoryInstitutionDTO> categories,
        String logoImage
) { }
