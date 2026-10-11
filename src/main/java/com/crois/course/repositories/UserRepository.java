package com.crois.course.repositories;


import com.crois.course.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Query("""
        SELECT u 
        FROM UserEntity u 
        WHERE u.username = :name
    """)
    UserEntity findByUsername(String name);

    @Query("""
           select u
           from UserEntity u
           where u.institutionId is null 
           """)
    List<UserEntity> getUserForManagers();
}
