package com.crois.course;


import com.crois.course.entity.PasswordEntity;
import com.crois.course.entity.Role;
import com.crois.course.entity.UserEntity;
import com.crois.course.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class Initializer {

    @Autowired
    private UserRepository userRepository;

    public void initial() {

        UserEntity admin = UserEntity.builder()
                .username("admin")
                .enabled(true)
                .roles(List.of(Role.ADMIN))
                .createdAt(new Date())
                .password(new PasswordEntity("admin123"))
                .build();

        userRepository.save(admin);

    }
}