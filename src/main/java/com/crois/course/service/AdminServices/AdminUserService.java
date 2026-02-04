package com.crois.course.service.AdminServices;

import com.crois.course.dto.UserDTO.UserDTOForAddManagerToInstitution;
import com.crois.course.mapper.UserMapper;
import com.crois.course.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDTOForAddManagerToInstitution> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(userMapper::createUserForAddToInstDTO)
                .toList();
    }

}
