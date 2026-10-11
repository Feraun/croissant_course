package com.crois.course.service.ClientServices;

import com.crois.course.dto.UserDTO.UserProfileDTO;
import com.crois.course.mapper.UserMapper;
import com.crois.course.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import static com.crois.course.Utils.IdUtils.getIdFromAuthentication;

@Service
@AllArgsConstructor
public class ClientProfileService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserProfileDTO getDataForProfilePage(Authentication authentication){
        return userMapper.createUserProfileDtoFromEntity(
                userRepository.getReferenceById(getIdFromAuthentication(authentication))
        );
    }

    //todo доделать историю заказов
    //    public List<BoxShortResponseDTO> getHistory(Authentication authentication){
    //        AuthUser authUser = (AuthUser) authentication.getPrincipal();
    //        return userRepository.findUserBoxHistory(authUser.getId());
    //    }
}
