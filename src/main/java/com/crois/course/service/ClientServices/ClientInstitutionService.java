package com.crois.course.service.ClientServices;

import com.crois.course.dto.InstitutionDTO.InstitutionResponseClient;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseDTO;
import com.crois.course.exceptions.NotFoundException;
import com.crois.course.mapper.InstitutionMapper;
import com.crois.course.repositories.InstitutionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@AllArgsConstructor
public class ClientInstitutionService {

    private final InstitutionMapper institutionMapper;
    private final InstitutionRepository institutionRepository;


    public Page<InstitutionResponseClient> searchInstitution(String name,
                                                             String address,
                                                             String city,
                                                             String contactNumber,
                                                             Long id,
                                                             Pageable pageable){

        return institutionRepository.searchInstitution(name, address, city, contactNumber, id, pageable)
                .map(institutionMapper::createDtoForClient);
    }

    public InstitutionResponseDTO getInstitutionById(@PathVariable("id") Long id){
        return(institutionMapper.createDtoFromEntity(institutionRepository.findById(id).orElseThrow(()->new NotFoundException("Institution not found"))));
    }

}
