package com.crois.course;


import com.crois.course.entity.PasswordEntity;
import com.crois.course.enums.Role;
import com.crois.course.entity.UserEntity;
import com.crois.course.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class Initializer {

    @Autowired
    private UserRepository userRepository;

    public void initial() {

        UserEntity admin = UserEntity.builder()
                .username("admin")
                .enabled(true)
                .role(Role.ADMIN)
                .firstName("grib")
                .lastName("artem")
                .contactNumber("52526")
                .email("sads@dsfs.com")

                .createdAt(LocalDateTime.now())
                .password(new PasswordEntity("admin123"))
                .build();

        userRepository.save(admin);

        UserEntity manager = UserEntity.builder()
                .username("manager")
                .enabled(true)
                .role(Role.MANAGER)
                .createdAt(LocalDateTime.now())
                .password(new PasswordEntity("1234"))
                .build();

        userRepository.save(manager);
    }
}