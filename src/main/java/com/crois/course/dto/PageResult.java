package com.crois.course.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class PageResult<T> {

    private List<T> items;

    private int page;
    private int size;
    private long totalItems;
    private int totalPages;

    public static <T> PageResult<T> of(
            List<T> content,
            int page,
            int size,
            long totalItems
    ) {
        int totalPages = (int) Math.ceil((double) totalItems / size);
        return new PageResult<>(content, page, size, totalItems, totalPages);
    }

}