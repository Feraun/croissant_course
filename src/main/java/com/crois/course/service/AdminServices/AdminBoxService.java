package com.crois.course.service.AdminServices;

import com.crois.course.dto.BoxDTO.BoxResponseDTO;
import com.crois.course.dto.BoxDTO.CreateBoxDTO;
import com.crois.course.entity.*;
import com.crois.course.exceptions.NotFoundException;
import com.crois.course.mapper.BoxMapper;
import com.crois.course.repositories.BoxRepository;
import com.crois.course.repositories.InstitutionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminBoxService {

    private final BoxMapper boxMapper;
    private final BoxRepository boxRepository;

    private final InstitutionRepository institutionRepository;

    public CreateBoxDTO createBox(Long institutionId, CreateBoxDTO createBoxDTO){
        BoxEntity boxEntity = boxMapper.createEntityFromDtoForNewBox(
                                createBoxDTO, institutionId);

        boxRepository.save(boxEntity);

        return boxMapper.createDtoFromEntity(boxEntity);

    }

    @Transactional
    public CreateBoxDTO editBox(Long institutionId, Long boxId, CreateBoxDTO createBoxDTO){

        //todo
        boxMapper.updateBox(boxRepository.getReferenceById(boxId), createBoxDTO);

        return createBoxDTO;
    }

    public BoxResponseDTO getBoxById(Long institutionId, Long boxId){
        return boxRepository.findByIdAndInstitutionId(boxId, institutionId)
                .map(boxMapper::createShortDtoFromEntity)
                .orElseThrow(() -> new NotFoundException("Box not found"));
    }

    public Long deleteBoxById(Long boxId) {
        if (boxRepository.deleteBoxById(boxId) == 1){
            return boxId;
        }
        else throw new NotFoundException("Box with id " + boxId + " not found");
    }
}
