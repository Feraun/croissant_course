package com.crois.course.service;

import com.crois.course.dto.BoxDTO.CreateBoxDTO;
import com.crois.course.dto.UserDTO.AuthUser;
import com.crois.course.entity.BoxEntity;
import com.crois.course.entity.InstitutionEntity;
import com.crois.course.mapper.BoxMapper;
import com.crois.course.mapper.CategoryInstitutionMapper;
import com.crois.course.mapper.CityMapper;
import com.crois.course.mapper.InstitutionMapper;
import com.crois.course.repositories.BoxRepository;
import com.crois.course.repositories.CategoryInstitutionRepository;
import com.crois.course.repositories.CityRepository;
import com.crois.course.repositories.InstitutionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class ManagerService {

    @PersistenceContext
    private EntityManager em;

    private final CityMapper cityMapper;
    private final CityRepository cityRepository;

    private final CategoryInstitutionMapper categoryInstitutionMapper;
    private final CategoryInstitutionRepository categoryInstitutionRepository;

    private final InstitutionMapper institutionMapper;
    private final InstitutionRepository institutionRepository;

    private final BoxMapper boxMapper;
    private final BoxRepository boxRepository;

    public CreateBoxDTO createBoxByManager(@PathVariable("institutionId") Long institutionId, @RequestBody CreateBoxDTO createBoxDTO, Authentication authentication){

        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        InstitutionEntity institution = institutionRepository.getReferenceById(institutionId);

        if (institution.getManagers().contains(authUser.getId())){
            BoxEntity boxEntity = boxMapper.createEntityFromDtoForNewBox(createBoxDTO);

            boxEntity.setInstitution(institutionRepository.getReferenceById(institutionId));

            boxMapper.setRoleForNewBox(boxEntity);

            boxRepository.save(boxEntity);

            return boxMapper.createDtoFromEntity(boxEntity);
        }
        else throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "Пользователь не является менеджером этого учреждения"
        );

    }

}
