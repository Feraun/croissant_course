package com.crois.course.entity;

import com.crois.course.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="username", unique = true)
    private String username;

    private boolean enabled;

    private String firstName;
    private String lastName;
    private String contactNumber;
    private String email;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "roles", nullable = false)
    private Role role;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "password_id", nullable = false)
    @JsonIgnore
    private PasswordEntity password;

    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private  List<OrderEntity> orders = new ArrayList<>();

    @OneToMany(
            mappedBy = "manager",
            fetch = FetchType.LAZY
    )
    private List<InstitutionEntity> institutionUnderUser;
}
