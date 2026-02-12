package com.crois.course.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "institutions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity manager;

    @Column(name = "user_id", nullable = false)
    private Long managerId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city_id", insertable = false, updatable = false)
    private CityEntity city;

    @Column(name = "city_id", nullable = false)
    private Long cityId;

    private String address;

    private Double rating;

    private String contactNumber;

    private LocalDateTime createdAt;

    @OneToMany(
            mappedBy = "institution",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Fetch(value = FetchMode.SUBSELECT)
    private  List<BoxEntity> boxes = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", insertable = false, updatable = false)
    private ImageEntity logo;

    @Column(name = "image_id", nullable = false)
    private UUID logoId;
}
