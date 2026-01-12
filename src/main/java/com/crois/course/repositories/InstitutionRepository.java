package com.crois.course.repositories;

import com.crois.course.entity.CityEntity;
import com.crois.course.entity.InstitutionEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InstitutionRepository extends JpaRepository<InstitutionEntity, Long> {

//    @Query("""
//        SELECT CASE WHEN :userId MEMBER OF i.managers THEN true ELSE false END
//        FROM InstitutionEntity i
//        WHERE i.id = :institutionId
//    """)
//    boolean existsManagerInInstitutionId(Long userId, Long institutionId);

}
