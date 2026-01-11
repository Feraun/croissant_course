package com.crois.course.repositories;

import com.crois.course.entity.CityEntity;
import com.crois.course.entity.InstitutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository extends JpaRepository<InstitutionEntity, Long> {
}
