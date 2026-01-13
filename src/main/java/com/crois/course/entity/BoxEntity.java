package com.crois.course.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "boxes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoxEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    private Double price;

    private Boolean randomly;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BoxStatus boxStatus;

    @ManyToOne()
    @JoinColumn(name = "institution_id", nullable = false)
    private InstitutionEntity institution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity owner;

    //todo доделать ProductEntity
}
