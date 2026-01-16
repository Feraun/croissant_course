package com.crois.course.service.AdminServices;

import com.crois.course.dto.BoxDTO.BoxShortResponseDTO;
import com.crois.course.dto.BoxDTO.CreateBoxDTO;
import com.crois.course.entity.BoxEntity;
import com.crois.course.mapper.BoxMapper;
import com.crois.course.repositories.BoxRepository;
import com.crois.course.repositories.InstitutionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class AdminBoxService {

    @PersistenceContext
    private EntityManager em;

    private final BoxMapper boxMapper;
    private final BoxRepository boxRepository;

    private final InstitutionRepository institutionRepository;

    @Transactional
    public CreateBoxDTO createBox(@PathVariable("institutionId") Long institutionId, @RequestBody CreateBoxDTO createBoxDTO){
        BoxEntity boxEntity = boxMapper.createEntityFromDtoForNewBox(createBoxDTO);

        boxEntity.setInstitution(institutionRepository.getReferenceById(institutionId));

        boxMapper.setRoleForNewBox(boxEntity);

        boxRepository.save(boxEntity);

        return boxMapper.createDtoFromEntity(boxEntity);

    }

    public CreateBoxDTO editBox(@PathVariable("institutionId") Long institutionId, @PathVariable("boxId") Long boxId, @RequestBody CreateBoxDTO createBoxDTO){
        BoxEntity boxEntity = boxRepository.findById(boxId).orElseThrow();

        boxEntity.setName(createBoxDTO.name());
        boxEntity.setDescription(createBoxDTO.description());
        boxEntity.setPrice(createBoxDTO.price());

        boxRepository.save(boxEntity);

        return boxMapper.createDtoFromEntity(boxEntity);
    }

    public BoxShortResponseDTO getBoxById(@PathVariable("institutionId") Long institutionId, @PathVariable("boxId") Long boxId){
        return boxRepository.findByIdAndInstitutionId(boxId, institutionId)
                .map(boxMapper::createShortDtoFromEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Box not found"));

    }

    public Long deleteBoxById(@PathVariable("institutionId") Long institutionId, @PathVariable("boxId") Long boxId){

        if (boxRepository.existsByIdAndInstitutionId(boxId, institutionId)){
            boxRepository.deleteById(boxId);
            return boxId;
        }
        else{
            throw new RuntimeException("Бокса нет");
        }
    }

}
