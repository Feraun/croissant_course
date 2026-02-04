package com.crois.course.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categoriesOfInstitution")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryInstitutionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @ManyToMany(mappedBy = "categories")
    private List<InstitutionEntity> institutions = new ArrayList<>();
}
