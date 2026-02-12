package com.crois.course.repositories;

import com.crois.course.entity.CategoryInstitutionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryInstitutionRepository extends JpaRepository<CategoryInstitutionEntity, Long> {

    @Query("""
            SELECT ce FROM CategoryInstitutionEntity ce
            WHERE (ce.id = coalesce(:categoryId, ce.id))
            AND lower(ce.name) LIKE lower(concat('%', coalesce(:name, ''), '%'))
            """)
    Page<CategoryInstitutionEntity> searchCategories(@Param("categoryId") Long categoryId,
                                  @Param("name") String name,
                                  Pageable pageable);

}
