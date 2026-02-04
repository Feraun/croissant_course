package com.crois.course.service;

import com.crois.course.dto.UserDTO.UserRegistrationRequestDTO;
import com.crois.course.entity.PasswordEntity;
import com.crois.course.entity.UserEntity;
import com.crois.course.enums.Role;
import com.crois.course.mapper.UserMapper;
import com.crois.course.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final UserMapper userRegistrationMapper;

    public RegistrationService(UserRepository userRepository1, UserMapper userRegistrationMapper){
        this.userRepository = userRepository1;
        this.userRegistrationMapper = userRegistrationMapper;
    }


    @Transactional
    public UserRegistrationRequestDTO createUser(UserRegistrationRequestDTO dto){

        UserEntity user = userRegistrationMapper.toUserRegistrationEntity(dto);

        user.setRole(Role.CLIENT);

        PasswordEntity password = new PasswordEntity(dto.password());

        user.setPassword(password);

        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        return userRegistrationMapper.toUserRegistrationDTO(user);
    }
}
