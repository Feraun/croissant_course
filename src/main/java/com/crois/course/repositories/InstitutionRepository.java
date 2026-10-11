package com.crois.course.repositories;

import com.crois.course.entity.InstitutionEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface InstitutionRepository extends JpaRepository<InstitutionEntity, Long> {

    boolean existsByIdAndManagersId(Long id, UUID managersId);

    //todo спецификация
    @Query("""
            SELECT ie FROM InstitutionEntity ie
            JOIN FETCH ie.categories
            JOIN FETCH ie.city c
            WHERE lower(ie.name) LIKE lower(concat('%', coalesce(:name, ''), '%'))
            AND lower(ie.address) LIKE lower(concat('%', coalesce(:address, ''), '%'))
            AND lower(ie.contactNumber) LIKE lower(concat('%', coalesce(:contactNumber, ''), '%'))
            AND ie.id = coalesce(:id, ie.id)
            AND lower(c.name) LIKE lower(concat('%', coalesce(:city, ''), '%'))
           """)
    Page<InstitutionEntity> searchInstitution(@Param("name") String name,
                                              @Param("address") String address,
                                              @Param("city") String city,
                                              @Param("contactNumber") String contactNumber,
                                              @Param("id") Long id,
                                              Pageable pageable);

    @Query("""
            SELECT ie FROM InstitutionEntity ie
            JOIN ie.managers m
            WHERE m.id = :managerId
            """)
    Page<InstitutionEntity> searchInstitutionByManager(@Param("managerId") UUID managerId,
                                                     Pageable pageable);

    @NotNull
    @Query("""
            SELECT ie FROM InstitutionEntity ie
            JOIN FETCH ie.categories
            WHERE ie.id = :institutionId
            """)
    InstitutionEntity getInstitutionsFetchCategories(@NotNull @Param("institutionId") Long institutionId);

    @NotNull
    @Query("""
            SELECT ie FROM InstitutionEntity ie
            JOIN FETCH ie.categories
            WHERE ie.id = :institutionId
            """)
    InstitutionEntity getInstitutionsFetchBoxes(@NotNull @Param("institutionId") Long institutionId);

}
