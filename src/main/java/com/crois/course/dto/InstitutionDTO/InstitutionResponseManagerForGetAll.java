package com.crois.course.dto.InstitutionDTO;

public record InstitutionResponseManagerForGetAll(
        Long id,
        String name,
        String address,
        Double rating
) {}
