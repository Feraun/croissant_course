package com.crois.course.dto;

/**
 * @param page номер страницы (0-based)
 * @param size размер страницы
 */

public record PageParams(int page,
                         int size,
                         String sortBy,
                         boolean asc) { }

