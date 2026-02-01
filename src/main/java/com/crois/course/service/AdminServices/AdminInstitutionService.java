package com.crois.course.service.AdminServices;

import com.crois.course.dto.InstitutionDTO.InstitutionRequestDTO;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseDTO;
import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
import com.crois.course.entity.*;
import com.crois.course.mapper.InstitutionMapper;
import com.crois.course.repositories.CategoryInstitutionRepository;
import com.crois.course.repositories.InstitutionRepository;
import com.crois.course.repositories.UserRepository;
import com.crois.course.service.MinioService;
import com.crois.course.service.SearchService.CriteriaFilter;
import com.crois.course.service.SearchService.CriteriaSearchUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminInstitutionService {

    @PersistenceContext
    private EntityManager em;

    private final InstitutionMapper institutionMapper;
    private final InstitutionRepository institutionRepository;

    private final UserRepository userRepository;

    private final CategoryInstitutionRepository categoryInstitutionRepository;

    private final MinioService minioService;

    //todo написать кастомный поиск заведений с joinom категорий для карточек на фронте
    public PageResult<InstitutionResponseDTO> searchInstitution(String name, PageParams params ){

        List<CriteriaFilter<InstitutionEntity>> filters = List.of(
                (cb, root, predicates) -> {
                    if (name != null && !name.isBlank()) {
                        predicates.add(
                                cb.like(
                                        cb.lower(root.get("name")),
                                        "%" + name.toLowerCase() + "%"
                                )
                        );
                    }
                }
        );

        return CriteriaSearchUtil.search(
                em,
                InstitutionEntity.class,
                filters,
                params,
                institutionMapper::createDtoFromEntity
        );
    }

    public InstitutionResponseDTO createInstitution(InstitutionRequestDTO institutionRequestDTO, MultipartFile multipartFile) throws Exception {

        InstitutionEntity institutionEntity = institutionMapper.createEntityFromDTO(institutionRequestDTO);

        List<CategoryInstitutionEntity> categoryInstitutionEntityList = institutionRequestDTO.categoryIds().stream()
                .map(categoryInstitutionRepository::getReferenceById)
                .toList();

        institutionEntity.setCategories(categoryInstitutionEntityList);

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

        institutionEntity.setManagers(userEntityList);

        institutionEntity.setCreatedAt(LocalDateTime.now());

        ImageEntity imageEntity = ImageEntity.builder()
                .id(UUID.randomUUID())
                .size(multipartFile.getSize())
                .httpContentType(multipartFile.getContentType())
                .build();


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

