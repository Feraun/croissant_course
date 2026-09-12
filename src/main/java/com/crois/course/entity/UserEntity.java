package com.crois.course.entity;

import com.crois.course.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="username", unique = true)
    private String username;

    @Column(name="keycloak_id", unique = true)
    private UUID keycloakId;

    private boolean enabled;

    private String firstName;
    private String lastName;
    private String contactNumber;
    private String email;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY
    )
    private  List<OrderEntity> orders = new ArrayList<>();

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(name = "institution_id", insertable = false, updatable = false)
    private InstitutionEntity institutionUnderUser;

    @Column(name = "institution_id")
    private Long institutionId;
}
