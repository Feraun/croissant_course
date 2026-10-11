package com.crois.course.service.AdminServices;

import com.crois.course.dto.UserDTO.UserDTOForAddManagerToInstitution;
import com.crois.course.dto.UserDTO.UserFullDTO;
import com.crois.course.entity.UserEntity;
import com.crois.course.exceptions.NotFoundException;
import com.crois.course.mapper.UserMapper;
import com.crois.course.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserFullDTO> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserFullDTO)
                .toList();
    }

    public List<UserDTOForAddManagerToInstitution> getUsersForManagers() {
        return userRepository.getUserForManagers().stream()
                .map(userMapper::createUserForAddManagerToInstitutionDTO)
                .toList();
    }

    public UserFullDTO getUserById(UUID id){
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return userMapper.toUserFullDTO(userEntity);
    }
}
