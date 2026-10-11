package com.crois.course.service.AdminServices;

import com.crois.course.dto.InstitutionDTO.InstitutionRequestDTO;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseDTO;
import com.crois.course.entity.CategoryInstitutionEntity;
import com.crois.course.entity.ImageEntity;
import com.crois.course.entity.InstitutionEntity;
import com.crois.course.entity.UserEntity;
import com.crois.course.enums.Role;
import com.crois.course.exceptions.NotFoundException;
import com.crois.course.mapper.InstitutionMapper;
import com.crois.course.repositories.CategoryInstitutionRepository;
import com.crois.course.repositories.ImageRepository;
import com.crois.course.repositories.InstitutionRepository;
import com.crois.course.repositories.UserRepository;
import com.crois.course.service.KeycloakService;
import com.crois.course.service.MinioService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AdminInstitutionService {

    private final InstitutionMapper institutionMapper;

    private final UserRepository userRepository;

    private final InstitutionRepository institutionRepository;

    private final ImageRepository imageRepository;

    private final CategoryInstitutionRepository categoryInstitutionRepository;

    private final MinioService minioService;

    private final KeycloakService keycloakService;

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


        ImageEntity imageEntity = ImageEntity.builder()
                .id(UUID.randomUUID())
                .size(multipartFile.getSize())
                .httpContentType(multipartFile.getContentType())
                .build();

        imageRepository.save(imageEntity);

        InstitutionEntity institutionEntity = institutionMapper.createEntityFromDTO(institutionRequestDTO,
                categoryInstitutionEntityList,
                LocalDateTime.now(),
                imageEntity.getId(),
                institutionRequestDTO.cityId()
        );


        institutionEntity = institutionRepository.save(institutionEntity);
        minioService.save(multipartFile, imageEntity.getId());

        List<UUID> managerUuids = institutionRequestDTO.managerId().stream()
                .map(UUID::fromString)
                .toList();

        List<UserEntity> managers = userRepository.findAllById(managerUuids);

        if (managers.size() != managerUuids.size()) {
            throw new NotFoundException("Один или несколько пользователей не найдены");
        }

        for (UserEntity manager : managers) {
            if (Objects.nonNull(manager.getInstitutionId())) {
                throw new BadRequestException("Пользователь " + manager.getUsername() + " уже привязан к другому заведению");
            }

            if (manager.getRole() == Role.CLIENT) {
                manager.setRole(Role.MANAGER);
                keycloakService.promoteClientToManager(manager.getId().toString());
            }

            manager.setInstitutionId(institutionEntity.getId());
            userRepository.save(manager);
        }

        return institutionMapper.createDtoFromEntity(institutionEntity);
    }

    @Transactional
    public InstitutionResponseDTO editInstitution(Long id, InstitutionRequestDTO institutionRequestDTO) {
        InstitutionEntity institutionEntity = institutionRepository.findById(id).orElseThrow();

        // 1. Преобразуем список строк в список UUID и достаем новых менеджеров
        List<UUID> newManagerIds = institutionRequestDTO.managerId().stream()
                .map(UUID::fromString)
                .toList();

        List<UserEntity> newManagers = userRepository.findAllById(newManagerIds);

        if (newManagers.size() != newManagerIds.size()) {
            throw new NotFoundException("Один или несколько новых менеджеров не найдены");
        }

        // 2. Получаем старых менеджеров и их ID
        List<UserEntity> oldManagers = institutionEntity.getManagers();
        List<UUID> oldManagerIds = oldManagers.stream()
                .map(UserEntity::getId)
                .toList();

        // 3. Отвязываем удаленных менеджеров
        for (UserEntity oldManager : oldManagers) {
            if (!newManagerIds.contains(oldManager.getId()) && oldManager.getRole() != Role.ADMIN) {
                oldManager.setRole(Role.CLIENT);
                oldManager.setInstitutionId(null);
                userRepository.save(oldManager);

                // Понижаем в Keycloak
                keycloakService.demoteManagerToClient(oldManager.getId().toString());
            }
        }

        // 4. Привязываем новых менеджеров
        for (UserEntity newManager : newManagers) {
            if (!oldManagerIds.contains(newManager.getId())) {
                if (newManager.getRole() == Role.CLIENT) {
                    newManager.setRole(Role.MANAGER);
                    keycloakService.promoteClientToManager(newManager.getId().toString());
                }
                newManager.setInstitutionId(institutionEntity.getId());
                userRepository.save(newManager);
            }
        }

        // 5. Обновляем само заведение (код маппера остается прежним)
        List<CategoryInstitutionEntity> categoryInstitutionEntityList = institutionRequestDTO.categoryIds().stream()
                .map(categoryInstitutionRepository::getReferenceById)
                .toList();

        InstitutionEntity institutionEntityUpdated = institutionMapper.updateEntity(
                institutionEntity,
                institutionRequestDTO,
                categoryInstitutionEntityList,
                institutionRequestDTO.cityId()
        );

        institutionRepository.save(institutionEntityUpdated);
        return institutionMapper.createDtoFromEntity(institutionEntityUpdated);
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

