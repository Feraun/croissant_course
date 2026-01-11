package com.crois.course.repositories;

import com.crois.course.entity.BoxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoxRepository extends JpaRepository<BoxEntity, Long> {


    // Spring сам поймет: "проверь существование по ID и полю institutionId"
    boolean existsByIdAndInstitutionId(Long id, Long institutionId);


    Optional<BoxEntity> findByIdAndInstitutionId(Long id, Long institutionId);

}
