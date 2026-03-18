package com.crois.course.dto.InstitutionDTO;

import java.util.UUID;

public record InstitutionResponseManagerForGetAll(
        Long id,
        String name,
        String address,
        Double rating,
        UUID logoId
) {}
