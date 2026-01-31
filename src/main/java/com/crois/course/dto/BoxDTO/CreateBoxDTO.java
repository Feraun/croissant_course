package com.crois.course.dto.BoxDTO;

public record CreateBoxDTO(
        String name,
        String description,
        Double price,
        Boolean randomly,
        Long institutionId,
        Integer quantity
        // бокс статус будет добавляться по умолчанию при создании

        // id заведения будет добавляться при создании, т.к. данный функционал будет доступен только через заведение
) {
}
