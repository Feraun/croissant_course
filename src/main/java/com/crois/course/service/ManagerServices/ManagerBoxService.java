package com.crois.course.service.ManagerServices;

import com.crois.course.dto.BoxDTO.CreateBoxDTO;
import com.crois.course.dto.BoxDTO.RandomBoxResponseDTO;
import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
import com.crois.course.dto.UserDTO.AuthUser;
import com.crois.course.entity.BoxEntity;
import com.crois.course.entity.InstitutionEntity;
import com.crois.course.entity.UserEntity;
import com.crois.course.mapper.BoxMapper;
import com.crois.course.repositories.BoxRepository;
import com.crois.course.repositories.InstitutionRepository;
import com.crois.course.service.SearchService.CriteriaFilter;
import com.crois.course.service.SearchService.CriteriaSearchUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Join;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

        //todo поменять проверку, т.к. теперь в заведение список сущностей
        if (institutionRepository.existsByIdAndManagerId(institutionId, authUser.getId())){
            BoxEntity boxEntity = boxMapper.createEntityFromDtoForNewBox(createBoxDTO);

            boxEntity.setInstitution(institutionRepository.getReferenceById(institutionId));

            boxRepository.save(boxEntity);

            return boxMapper.createDtoFromEntity(boxEntity);
        }
        else throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "Пользователь не является менеджером этого учреждения"
        );
    }

    public Page<RandomBoxResponseDTO> getAllBox(Long institutionId, Long boxId, Authentication authentication, Pageable pageable){

        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        return boxRepository.getBoxFromInstitutionByManager(institutionId, authUser.getId(), boxId, pageable)
                .map(boxMapper::createManagersDtoFromEntity);
    }

}
