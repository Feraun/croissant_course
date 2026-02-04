package com.crois.course.repositories;

import com.crois.course.entity.BoxEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoxRepository extends JpaRepository<BoxEntity, Long> {


    // Spring сам поймет: "проверь существование по ID и полю institutionId"
    @EntityGraph(attributePaths = "institution")
    boolean existsByIdAndInstitutionId(Long id, Long institutionId);

    @EntityGraph(attributePaths = "institution")
    Optional<BoxEntity> findByIdAndInstitutionId(Long id, Long institutionId);


    @Query("""
            SELECT be FROM BoxEntity be
            JOIN FETCH be.institution i
            JOIN FETCH i.manager m
            WHERE i.id = :institutionId and m.id = :managerId and be.id = coalesce(:boxId, be.id)
            """)
    Page<BoxEntity> getBoxFromInstitutionByManager(@Param("institutionId") Long institutionId,
                                                   @Param("managerId") Long managerId,
                                                   @Param("boxId") Long boxId,
                                                   Pageable pageable);
}
