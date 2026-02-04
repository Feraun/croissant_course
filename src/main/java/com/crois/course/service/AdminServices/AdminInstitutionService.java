package com.crois.course.service.AdminServices;

import com.crois.course.dto.InstitutionDTO.InstitutionRequestDTO;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseDTO;
import com.crois.course.entity.*;
import com.crois.course.enums.Role;
import com.crois.course.mapper.InstitutionMapper;
import com.crois.course.repositories.CategoryInstitutionRepository;
import com.crois.course.repositories.CityRepository;
import com.crois.course.repositories.InstitutionRepository;
import com.crois.course.repositories.UserRepository;
import com.crois.course.service.MinioService;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminInstitutionService {

    private final InstitutionMapper institutionMapper;
    private final InstitutionRepository institutionRepository;

    private final CityRepository cityRepository;

    private final UserRepository userRepository;

    private final CategoryInstitutionRepository categoryInstitutionRepository;

    private final MinioService minioService;

    public Page<InstitutionResponseDTO> searchInstitution(String name,
                                                         String address,
                                                         String city,
                                                         String contactNumber,
                                                         Long id,
                                                         Pageable pageable){

        return institutionRepository.searchInstitution(name, address, city, contactNumber, id, pageable)
                .map(institutionMapper::createDtoFromEntity);
    }

    public InstitutionResponseDTO createInstitution(InstitutionRequestDTO institutionRequestDTO, MultipartFile multipartFile) throws Exception {

        List<CategoryInstitutionEntity> categoryInstitutionEntityList = institutionRequestDTO.categoryIds().stream()
                .map(categoryInstitutionRepository::getReferenceById)
                .toList();

        UserEntity manager = userRepository.getReferenceById(institutionRequestDTO.managerId());

        if(manager.getRole().equals(Role.CLIENT)) {
            manager.setRole(Role.MANAGER);
        }

        CityEntity cityEntity = cityRepository.getReferenceById(institutionRequestDTO.cityId());

        ImageEntity imageEntity = ImageEntity.builder()
                .id(UUID.randomUUID())
                .size(multipartFile.getSize())
                .httpContentType(multipartFile.getContentType())
                .build();

        InstitutionEntity institutionEntity = institutionMapper.createEntityFromDTO(institutionRequestDTO,
                categoryInstitutionEntityList,
                manager,
                LocalDateTime.now(),
                imageEntity,
                cityEntity
        );

        institutionRepository.save(institutionEntity);

        minioService.save(multipartFile, imageEntity.getId());

        return institutionMapper.createDtoFromEntity(institutionEntity);
    }

    //todo надо оптимизировать
    public InstitutionResponseDTO editInstitution(Long id, InstitutionRequestDTO institutionRequestDTO){
        InstitutionEntity institutionEntity = institutionRepository.findById(id).orElseThrow();

        UserEntity managerOld = institutionEntity.getManager();
        UserEntity managerNew = userRepository.getReferenceById(institutionRequestDTO.managerId());

        if(!managerOld.getId().equals(institutionRequestDTO.managerId())){
            managerOld.setRole(Role.CLIENT);

            if(managerNew.getRole().equals(Role.CLIENT)){
                managerNew.setRole(Role.MANAGER);
            }
        }

        List<CategoryInstitutionEntity> categoryInstitutionEntityList = institutionRequestDTO.categoryIds().stream()
                .map(categoryInstitutionRepository::getReferenceById)
                .toList();

        CityEntity cityEntity = cityRepository.getReferenceById(institutionRequestDTO.cityId());

        UserEntity manager = userRepository.getReferenceById(institutionRequestDTO.managerId());

        InstitutionEntity institutionEntityUpdated = institutionMapper.updateEntity(institutionEntity,
                institutionRequestDTO,
                categoryInstitutionEntityList,
                manager,
                cityEntity);

        institutionRepository.save(institutionEntityUpdated);

        return(institutionMapper.createDtoFromEntity(institutionEntityUpdated));
    }

    public InstitutionResponseDTO getInstitutionById(Long id){
        return(institutionMapper.createDtoFromEntity(institutionRepository.findById(id).orElseThrow()));
    }

    public Long deleteInstitutionById(Long id){
        institutionRepository.deleteById(id);
        return (id);
    }



    //todo editUser
    //todo deleteUser
}

