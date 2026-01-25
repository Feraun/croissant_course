package com.crois.course.repositories;

import com.crois.course.dto.BoxDTO.BoxShortResponseDTO;
import com.crois.course.entity.BoxEntity;
import com.crois.course.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u " +
            " FROM UserEntity u " +
            " JOIN FETCH u.password " +
            " WHERE u.username = :name")
    UserEntity findByUsername(String name);

}
