package com.crois.course.service.AdminServices;

import com.crois.course.dto.InstitutionDTO.InstitutionRequestDTO;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseClient;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseDTO;
import com.crois.course.entity.*;
import com.crois.course.mapper.InstitutionMapper;
import com.crois.course.repositories.CategoryInstitutionRepository;
import com.crois.course.repositories.InstitutionRepository;
import com.crois.course.repositories.UserRepository;
import com.crois.course.service.MinioService;
import lombok.AllArgsConstructor;
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

    private final UserRepository userRepository;

    private final CategoryInstitutionRepository categoryInstitutionRepository;

    private final MinioService minioService;

    //todo написать кастомный поиск заведений с joinom категорий для карточек на фронте
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

        List<UserEntity> userEntityList = institutionRequestDTO.managersIds().stream()
                .map(userRepository::getReferenceById)
                .peek(user -> {
                    List<Role> roles = new ArrayList<>(user.getRoles());
                    if (!roles.contains(Role.MANAGER)) {
                        roles.add(Role.MANAGER);
                        user.setRoles(roles);
                    }
                })
                .toList();


        ImageEntity imageEntity = ImageEntity.builder()
                .id(UUID.randomUUID())
                .size(multipartFile.getSize())
                .httpContentType(multipartFile.getContentType())
                .build();

        //todo (institutionRequestDTO, categoryInstitutionEntityList, userEntityList, LocalDateTime.now(), imageEntity

        InstitutionEntity institutionEntity = institutionMapper.createEntityFromDTO(institutionRequestDTO);



        institutionEntity.setCategories(categoryInstitutionEntityList);
        institutionEntity.setManagers(userEntityList);
        institutionEntity.setCreatedAt(LocalDateTime.now());
        institutionEntity.setLogo(imageEntity);



        institutionRepository.save(institutionEntity);

        minioService.save(multipartFile, imageEntity.getId());

        return institutionMapper.createDtoFromEntity(institutionEntity);
    }

    public InstitutionResponseDTO editInstitution(Long id, InstitutionRequestDTO institutionRequestDTO){
        InstitutionEntity institutionEntity = institutionRepository.findById(id).orElseThrow();

        institutionEntity.setName(institutionRequestDTO.name());
        institutionEntity.setCityId(institutionRequestDTO.cityId());
        institutionEntity.setAddress(institutionRequestDTO.address());

        List<CategoryInstitutionEntity> categoryInstitutionEntityList = institutionRequestDTO.categoryIds().stream()
                .map(categoryInstitutionRepository::getReferenceById)
                .collect(Collectors.toList());

        institutionEntity.setCategories(categoryInstitutionEntityList);

        institutionEntity.setContactNumber(institutionRequestDTO.contactNumber());

        List<UserEntity> userEntityList = institutionRequestDTO.managersIds().stream()
                .map(userRepository::getReferenceById)
                .collect(Collectors.toList());

        institutionEntity.setManagers(userEntityList);

        //todo придумать алгоритм расчета рейтинга на основе отзывов
        institutionEntity.setRating(institutionRequestDTO.rating());

        institutionRepository.save(institutionEntity);

        return(institutionMapper.createDtoFromEntity(institutionEntity));
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

