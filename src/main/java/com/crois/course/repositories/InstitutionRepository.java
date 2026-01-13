package com.crois.course.repositories;

import com.crois.course.entity.CityEntity;
import com.crois.course.entity.InstitutionEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InstitutionRepository extends JpaRepository<InstitutionEntity, Long> {

    @Query("SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END " +
            "FROM InstitutionEntity i JOIN i.managers m " +
            "WHERE i.id = :institutionId AND m.id = :managerId")
    boolean existsByIdAndManagerId(@Param("institutionId") Long institutionId,
                                   @Param("managerId") Long managerId);
}
