package com.crois.course.service;

import com.crois.course.entity.UserEntity;
import com.crois.course.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HelperService {

    private final UserRepository userRepository;

    @Transactional
    public void saveUser(UserEntity currentUser) {
        userRepository.save(currentUser);
    }
}
