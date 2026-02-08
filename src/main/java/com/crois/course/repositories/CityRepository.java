package com.crois.course.repositories;

import com.crois.course.entity.CityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CityRepository extends JpaRepository<CityEntity, Long> {

    @Query("""
            SELECT ce FROM CityEntity ce
            WHERE (ce.id = coalesce(:cityId, ce.id))
            AND lower(ce.name) LIKE lower(concat('%', coalesce(:name, ''), '%'))
            """)
    Page<CityEntity> searchCities(@Param("cityId") Long cityId,
                                  @Param("name") String cityName,
                                  Pageable pageable);

}
