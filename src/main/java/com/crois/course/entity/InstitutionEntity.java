package com.crois.course.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "institutions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstitutionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "institution_categories",
            joinColumns = @JoinColumn(name = "institution_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<CategoryInstitutionEntity> categories = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "institution_managers",
            joinColumns = @JoinColumn(name = "institution_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> managers = new ArrayList<>();
    ;

    @Column(name = "city_id", nullable = false)
    private Long cityId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city_id", insertable = false, updatable = false)
    private CityEntity city;

    private String address;

    private Double rating;

    private String contactNumber;

    private LocalDateTime createdAt;

    @OneToMany(
            mappedBy = "institution",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private  List<BoxEntity> boxes = new ArrayList<>();


}
