package com.crois.course.service.ManagerServices;

import com.crois.course.dto.BoxDTO.BoxResponseDTO;
import com.crois.course.dto.BoxDTO.CreateBoxDTO;
import com.crois.course.dto.UserDTO.AuthUser;
import com.crois.course.entity.BoxEntity;
import com.crois.course.mapper.BoxMapper;
import com.crois.course.repositories.BoxRepository;
import com.crois.course.repositories.InstitutionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class ManagerBoxService {

    @PersistenceContext
    private EntityManager em;

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
        else throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "Пользователь не является менеджером этого учреждения"
        );
    }

    public Page<BoxResponseDTO> getAllBox(Long institutionId, Long boxId, Authentication authentication, Pageable pageable){

        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        return boxRepository.getBoxFromInstitutionByManager(institutionId, authUser.getId(), boxId, pageable)
                .map(boxMapper::createManagersDtoFromEntity);
    }

}
