package com.crois.course.service.ManagerServices;

import com.crois.course.dto.BoxDTO.BoxResponseDTO;
import com.crois.course.dto.BoxDTO.CreateBoxDTO;
import com.crois.course.dto.UserDTO.AuthUser;
import com.crois.course.entity.BoxEntity;
import com.crois.course.exceptions.YouNotHaveAccessException;
import com.crois.course.mapper.BoxMapper;
import com.crois.course.repositories.BoxRepository;
import com.crois.course.repositories.InstitutionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ManagerBoxService {

    private final BoxMapper boxMapper;
    private final BoxRepository boxRepository;

    private final InstitutionRepository institutionRepository;

    public CreateBoxDTO createBoxByManager(Long institutionId, CreateBoxDTO createBoxDTO, Authentication authentication){

        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        if (institutionRepository.existsByIdAndManagerId(institutionId, authUser.getId())){
            BoxEntity boxEntity = boxMapper.createEntityFromDtoForNewBox(
                    createBoxDTO,
                    institutionRepository.getReferenceById(institutionId));

            boxRepository.save(boxEntity);

            return boxMapper.createDtoFromEntity(boxEntity);
        }
        throw new YouNotHaveAccessException("Пользователь не является менеджером указанного заведения");

    }

    public Page<BoxResponseDTO> getAllBox(Long institutionId, Long boxId, Authentication authentication, Pageable pageable){

        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        return boxRepository.getBoxFromInstitutionByManager(institutionId, authUser.getId(), boxId, pageable)
                .map(boxMapper::createManagersDtoFromEntity);
    }
}
