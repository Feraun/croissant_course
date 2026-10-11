package com.crois.course.repositories;

import com.crois.course.entity.BoxEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BoxRepository extends JpaRepository<BoxEntity, Long> {

    Optional<BoxEntity> findByIdAndInstitutionId(Long id, Long institutionId);

    @Modifying
    @Transactional
    @Query("""
            DELETE FROM BoxEntity be
            WHERE be.id = :boxId
            """)
    int deleteBoxById(@Param("boxId") Long boxId);

    @Query("""
            SELECT be FROM BoxEntity be
            JOIN be.institution i
            WHERE i.id = :institutionId
            """)
    List<BoxEntity> getBoxesFromInstitutions(@Param("institutionId") Long institutionId);

    @Query("""
        SELECT be FROM BoxEntity be
        JOIN FETCH be.institution i
        JOIN i.managers m
        WHERE i.id = :institutionId AND m.id = :managerId
        AND be.id = coalesce(:boxId, be.id)
        """)
    Page<BoxEntity> getBoxFromInstitutionByManager(@Param("institutionId") Long institutionId,
                                                   @Param("managerId") UUID managerId,
                                                   @Param("boxId") Long boxId,
                                                   Pageable pageable);

}
