package com.crois.course.repositories;

import com.crois.course.entity.BoxEntity;
import com.crois.course.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoxRepository extends JpaRepository<BoxEntity, Long> {

    Optional<BoxEntity> findByIdAndInstitutionId(Long id, Long institutionId);

    @Modifying
    @Transactional
    @Query("""
            DELETE FROM BoxEntity be
            WHERE be.id = :boxId
            AND be.institution.id = :institutionId
            """)
    int deleteBoxById(@Param("boxId") Long boxId,
                       @Param("institutionId") Long institutionId) throws NotFoundException;


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
