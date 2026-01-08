package com.crois.course.dto.BoxDTO;

import com.crois.course.entity.BoxStatus;

public record CreateBoxDTO(
        String name,
        String description,
        Double price,
        Boolean randomly
        // бокс статус будет добавляться по умолчанию при создании

        // id заведения будет добавляться при создании, т.к. данный функционал будет доступен только через заведение
) {
}
