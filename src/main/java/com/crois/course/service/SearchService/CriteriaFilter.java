package com.crois.course.service.SearchService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

@FunctionalInterface
public interface CriteriaFilter<E> {

    void apply(
            CriteriaBuilder cb,
            Root<E> root,
            List<Predicate> predicates
    );
}

