package com.crois.course.service.AdminServices;

import com.crois.course.dto.BoxDTO.BoxShortResponseDTO;
import com.crois.course.dto.BoxDTO.CreateBoxDTO;
import com.crois.course.entity.*;
import com.crois.course.mapper.BoxMapper;
import com.crois.course.repositories.BoxRepository;
import com.crois.course.repositories.InstitutionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class AdminBoxService {

    private final BoxMapper boxMapper;
    private final BoxRepository boxRepository;

    private final InstitutionRepository institutionRepository;

    public CreateBoxDTO createBox(Long institutionId, CreateBoxDTO createBoxDTO){
        BoxEntity boxEntity = boxMapper.createEntityFromDtoForNewBox(
                                createBoxDTO,
                                institutionRepository.getReferenceById(institutionId));

        boxRepository.save(boxEntity);

        return boxMapper.createDtoFromEntity(boxEntity);

    }

    public CreateBoxDTO editBox(Long institutionId, Long boxId, CreateBoxDTO createBoxDTO){
        BoxEntity boxEntity = boxRepository.findById(boxId).orElseThrow();

        boxEntity.setName(createBoxDTO.name());
        boxEntity.setDescription(createBoxDTO.description());
        boxEntity.setPrice(createBoxDTO.price());

        boxRepository.save(boxEntity);

        return boxMapper.createDtoFromEntity(boxEntity);
    }

    public BoxShortResponseDTO getBoxById(Long institutionId, Long boxId){
        return boxRepository.findByIdAndInstitutionId(boxId, institutionId)
                .map(boxMapper::createShortDtoFromEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Box not found"));

    }

    public Long deleteBoxById(Long institutionId, Long boxId){

        //todo сделать sql запрос с поиском и удалением сразу
        if (boxRepository.existsByIdAndInstitutionId(boxId, institutionId)){
            boxRepository.deleteById(boxId);
            return boxId;
        }
        else{
            throw new RuntimeException("Бокса нет");
        }
    }
}
