package com.crois.course.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "boxes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoxEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String description;

    @Column(nullable = false)
    private Double price;

    private Boolean randomly;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "institution_id", insertable = false, updatable = false)
    private InstitutionEntity institution;

    @Column(name = "institution_id", nullable = false)
    private Long institutionId;
}
