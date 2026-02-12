package com.crois.course.service.AdminServices;

import com.crois.course.dto.InstitutionDTO.InstitutionRequestDTO;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseDTO;
import com.crois.course.entity.*;
import com.crois.course.enums.Role;
import com.crois.course.exceptions.NotFoundException;
import com.crois.course.mapper.InstitutionMapper;
import com.crois.course.repositories.*;
import com.crois.course.service.MinioService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AdminInstitutionService {

    private final InstitutionMapper institutionMapper;
    private final InstitutionRepository institutionRepository;

    private final UserRepository userRepository;

    private final CategoryInstitutionRepository categoryInstitutionRepository;

    private final MinioService minioService;

    private final ImageRepository imageRepository;
    public Page<InstitutionResponseDTO> searchInstitution(String name,
                                                         String address,
                                                         String city,
                                                         String contactNumber,
                                                         Long id,
                                                         Pageable pageable){

        return institutionRepository.searchInstitution(name, address, city, contactNumber, id, pageable)
                .map(institutionMapper::createDtoFromEntity);
    }

    @Transactional
    public InstitutionResponseDTO createInstitution(
            InstitutionRequestDTO institutionRequestDTO,
            MultipartFile multipartFile) throws Exception
    {

        List<CategoryInstitutionEntity> categoryInstitutionEntityList = institutionRequestDTO.categoryIds().stream()
                .map(categoryInstitutionRepository::getReferenceById)
                .toList();

        UserEntity manager = userRepository.findById(institutionRequestDTO.managerId())
                .orElseThrow(
                () -> new NotFoundException("User with id " + institutionRequestDTO.managerId() + " not found"));

        if(manager.getRole().equals(Role.CLIENT)) {
            manager.setRole(Role.MANAGER);
        }

        ImageEntity imageEntity = ImageEntity.builder()
                .id(UUID.randomUUID())
                .size(multipartFile.getSize())
                .httpContentType(multipartFile.getContentType())
                .build();

        imageRepository.save(imageEntity);

        InstitutionEntity institutionEntity = institutionMapper.createEntityFromDTO(institutionRequestDTO,
                categoryInstitutionEntityList,
                institutionRequestDTO.managerId(),
                LocalDateTime.now(),
                imageEntity.getId(),
                institutionRequestDTO.cityId()
        );

        institutionRepository.save(institutionEntity);
        minioService.save(multipartFile, imageEntity.getId());

        return institutionMapper.createDtoFromEntity(institutionEntity);
    }

    @Transactional
    public InstitutionResponseDTO editInstitution(Long id, InstitutionRequestDTO institutionRequestDTO){
        InstitutionEntity institutionEntity = institutionRepository.findById(id).orElseThrow();

        UserEntity managerOld = institutionEntity.getManager();
        UserEntity managerNew = userRepository.getReferenceById(institutionRequestDTO.managerId());

        if(!managerOld.getId().equals(institutionRequestDTO.managerId()) && !managerOld.getRole().equals(Role.ADMIN)){
            managerOld.setRole(Role.CLIENT);

            if(managerNew.getRole().equals(Role.CLIENT)){
                managerNew.setRole(Role.MANAGER);
            }
        }

        List<CategoryInstitutionEntity> categoryInstitutionEntityList = institutionRequestDTO.categoryIds().stream()
                .map(categoryInstitutionRepository::getReferenceById)
                .toList();

        InstitutionEntity institutionEntityUpdated = institutionMapper.updateEntity(institutionEntity,
                institutionRequestDTO,
                categoryInstitutionEntityList,
                managerNew.getId(),
                institutionRequestDTO.cityId());

        institutionRepository.save(institutionEntityUpdated);

        return(institutionMapper.createDtoFromEntity(institutionEntityUpdated));
    }

    public InstitutionResponseDTO getInstitutionById(Long id){
        return(institutionMapper.createDtoFromEntity(
                institutionRepository.findById(id)
                        .orElseThrow(()->new NotFoundException("Institution not found")))
        );
    }

    public Long deleteInstitutionById(Long id){
        institutionRepository.deleteById(id);
        return (id);
    }



    //todo editUser
    //todo deleteUser
}

