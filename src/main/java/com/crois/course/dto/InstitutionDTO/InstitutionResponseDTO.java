package com.crois.course.dto.InstitutionDTO;

import com.crois.course.dto.BoxDTO.BoxShortResponseDTO;
import com.crois.course.dto.CityDTO.CityDTO;

import java.util.List;

public record InstitutionResponseDTO(
        String name,
        CityDTO city,
        String address,
        Double rating,
        String contactNumber,
        List<BoxShortResponseDTO> boxes
) { }
