package com.crois.course.service.ClientServices;

import com.crois.course.dto.BoxDTO.BoxShortResponseDTO;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseClient;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseDTO;
import com.crois.course.dto.OrderDTO;
import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
import com.crois.course.dto.UserDTO.AuthUser;
import com.crois.course.dto.UserDTO.UserProfileDTO;
import com.crois.course.entity.*;
import com.crois.course.mapper.BoxMapper;
import com.crois.course.mapper.InstitutionMapper;
import com.crois.course.mapper.OrderMapper;
import com.crois.course.mapper.UserMapper;
import com.crois.course.repositories.*;
import com.crois.course.service.SearchService.CriteriaFilter;
import com.crois.course.service.SearchService.CriteriaSearchUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ClientService {







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
