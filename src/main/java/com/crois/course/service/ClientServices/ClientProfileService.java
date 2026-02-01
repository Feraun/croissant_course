package com.crois.course.service.ClientServices;

import com.crois.course.dto.UserDTO.AuthUser;
import com.crois.course.dto.UserDTO.UserProfileDTO;
import com.crois.course.entity.*;
import com.crois.course.mapper.UserMapper;
import com.crois.course.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClientProfileService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserProfileDTO getDataForProfilePage(Authentication authentication){
        AuthUser authUser = (AuthUser)  authentication.getPrincipal();

        UserEntity user = userRepository.getReferenceById(authUser.getId());

        return userMapper.createUserProfileDtoFromEntity(user);

    }

    //todo доделать историю заказов
    //    public List<BoxShortResponseDTO> getHistory(Authentication authentication){
    //        AuthUser authUser = (AuthUser) authentication.getPrincipal();
    //        return userRepository.findUserBoxHistory(authUser.getId());
    //    }
}
