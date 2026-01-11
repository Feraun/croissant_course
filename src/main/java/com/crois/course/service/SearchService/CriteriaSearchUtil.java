package com.crois.course.service.SearchService;

import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class CriteriaSearchUtil {

    private CriteriaSearchUtil() {}

    public static <E, D> PageResult<D> search(
            EntityManager em,
            Class<E> entityClass,
            List<CriteriaFilter<E>> filters,
            PageParams params,
            Function<E, D> mapper
    ) {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        /* =======================
           MAIN QUERY
           ======================= */
        CriteriaQuery<E> cq = cb.createQuery(entityClass);
        Root<E> root = cq.from(entityClass);
        cq.select(root);

        /* =======================
           COUNT QUERY
           ======================= */
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<E> countRoot = countQuery.from(entityClass);
        countQuery.select(cb.count(countRoot));

        List<Predicate> predicates = new ArrayList<>();
        List<Predicate> countPredicates = new ArrayList<>();

        /* =======================
           FILTERS
           ======================= */
        for (CriteriaFilter<E> filter : filters) {
            filter.apply(cb, root, predicates);
            filter.apply(cb, countRoot, countPredicates);
        }

        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(Predicate[]::new)));
        }

        if (!countPredicates.isEmpty()) {
            countQuery.where(cb.and(countPredicates.toArray(Predicate[]::new)));
        }

        /* =======================
           SORT
           ======================= */
        if (params.sortBy() != null && !params.sortBy().isBlank()) {
            Path<Object> sortPath = root.get(params.sortBy());
            cq.orderBy(params.asc()
                    ? cb.asc(sortPath)
                    : cb.desc(sortPath)
            );
        }

        /* =======================
           PAGINATION
           ======================= */
        TypedQuery<E> query = em.createQuery(cq);
        query.setFirstResult(params.page() * params.size());
        query.setMaxResults(params.size());

        List<D> result = query.getResultList()
                .stream()
                .map(mapper)
                .toList();

        long totalItems = em.createQuery(countQuery).getSingleResult();

        return PageResult.of(
                result,
                params.page(),
                params.size(),
                totalItems
        );
    }
}
