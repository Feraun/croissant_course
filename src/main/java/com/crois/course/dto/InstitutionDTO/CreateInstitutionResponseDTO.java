package com.crois.course.dto.InstitutionDTO;

import com.crois.course.dto.CategoryInstitutionDTO.CategoryInstitionDTO;
import com.crois.course.dto.CityDTO.CityDTO;

import java.util.Date;
import java.util.List;

public record CreateInstitutionResponseDTO(
        Long id,
        String name,
        CityDTO city,
        String address,
        Double rating,
        String contactNumber,
        Date createdAt,
        List<CategoryInstitionDTO> categories
) { }
