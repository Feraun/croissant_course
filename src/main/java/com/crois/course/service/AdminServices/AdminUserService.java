package com.crois.course.service.AdminServices;

import com.crois.course.dto.UserDTO.UserForAddToInst;
import com.crois.course.mapper.UserMapper;
import com.crois.course.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminUserService {

    @PersistenceContext
    private EntityManager em;

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserForAddToInst> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(userMapper::createUserForAddToInstDTO)
                .toList();
    }

}
