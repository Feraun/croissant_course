package com.crois.course.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    private Date createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "roles", nullable = false)
    private List<Role> roles;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "password_id", nullable = false)
    @JsonIgnore
    private PasswordEntity password;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<BoxEntity> history_of_boxes = new ArrayList<>();

}
