package com.crois.course.service.ClientServices;

import com.crois.course.dto.InstitutionDTO.InstitutionResponseClient;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseClientGetAll;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseDTO;
import com.crois.course.entity.BoxEntity;
import com.crois.course.entity.InstitutionEntity;
import com.crois.course.exceptions.NotFoundException;
import com.crois.course.mapper.InstitutionMapper;
import com.crois.course.repositories.BoxRepository;
import com.crois.course.repositories.InstitutionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@AllArgsConstructor
public class ClientInstitutionService {

    private final InstitutionMapper institutionMapper;
    private final InstitutionRepository institutionRepository;

    private final BoxRepository boxRepository;


    public Page<InstitutionResponseClientGetAll> searchInstitution(String name,
                                                                   String address,
                                                                   String city,
                                                                   String contactNumber,
                                                                   Long id,
                                                                   Pageable pageable){

        return institutionRepository.searchInstitution(name, address, city, contactNumber, id, pageable)
                .map(institutionMapper::createDtoForClient);
    }

    public InstitutionResponseClient getInstitutionById(@PathVariable("id") Long id){

        List<BoxEntity> boxEntityList = boxRepository.getBoxesFromInstitutions(id);

        InstitutionEntity institutionEntity = institutionRepository.getInstitutionsFetchCategories(id);



        return institutionMapper.createDtoForClientById(institutionEntity, boxEntityList);
    }

}
