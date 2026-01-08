package com.crois.course.service;

import com.crois.course.dto.UserDTO.UserRegistrationRequestDTO;
import com.crois.course.entity.PasswordEntity;
import com.crois.course.entity.UserEntity;
import com.crois.course.mapper.UserRegistrationMapper;
import com.crois.course.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final UserRegistrationMapper userRegistrationMapper;

    public RegistrationService(UserRepository userRepository1, UserRegistrationMapper userRegistrationMapper){
        this.userRepository = userRepository1;
        this.userRegistrationMapper = userRegistrationMapper;
    }


    @Transactional
    public UserRegistrationRequestDTO createUser(@RequestBody UserRegistrationRequestDTO dto){

        UserEntity user = userRegistrationMapper.toUserRegistrationEntity(dto);

        userRegistrationMapper.setRolesFromDTO(dto, user);

        PasswordEntity password = new PasswordEntity(dto.password());

        user.setPassword(password);

        userRepository.save(user);

        return userRegistrationMapper.toUserRegistrationDTO(user);
    }
}
