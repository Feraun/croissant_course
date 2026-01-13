package com.crois.course.service;

import com.crois.course.dto.BoxDTO.BoxShortResponseDTO;
import com.crois.course.dto.BoxDTO.CreateBoxDTO;
import com.crois.course.dto.CategoryInstitutionDTO.CategoryInstitutionDTO;
import com.crois.course.dto.CityDTO.CityDTO;
import com.crois.course.dto.InstitutionDTO.InstitutionRequestDTO;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseDTO;
import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
import com.crois.course.entity.*;
import com.crois.course.mapper.BoxMapper;
import com.crois.course.mapper.CategoryInstitutionMapper;
import com.crois.course.mapper.CityMapper;
import com.crois.course.mapper.InstitutionMapper;
import com.crois.course.repositories.*;
import com.crois.course.service.SearchService.CriteriaFilter;
import com.crois.course.service.SearchService.CriteriaSearchUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {

    @PersistenceContext
    private EntityManager em;

    private final CityMapper cityMapper;
    private final CityRepository cityRepository;

    private final CategoryInstitutionMapper categoryInstitutionMapper;
    private final CategoryInstitutionRepository categoryInstitutionRepository;

    private final InstitutionMapper institutionMapper;
    private final InstitutionRepository institutionRepository;

    private final BoxMapper boxMapper;
    private final BoxRepository boxRepository;

    private final UserRepository userRepository;

    public CityDTO createCity(@RequestBody CityDTO cityDTO){
        CityEntity cityEntity = cityMapper.createEntityFromDTO(cityDTO);
        cityRepository.save(cityEntity);
        return (cityMapper.createDtoFromEntity(cityEntity));
    }

    public CityDTO editCity(@PathVariable("id") Long id,  @RequestBody CityDTO cityDTO){
        //todo кастомные эксепшн
        CityEntity cityEntity = cityRepository.findById(id).orElseThrow();

        cityEntity.setRegion(cityDTO.region());
        cityEntity.setName(cityDTO.name());

        cityRepository.save(cityEntity);
        return (cityMapper.createDtoFromEntity(cityEntity));

    }

    public CityDTO getCityById(@PathVariable("id") Long id){
        return (cityMapper.createDtoFromEntity(cityRepository.findById(id).orElseThrow()));
    }

    public String deleteCityById(@PathVariable("id") Long id){
        cityRepository.deleteById(id);
        return ("City with id: '" + id + "' was deleted");
    }

    public PageResult<CityDTO> searchCity(String name, PageParams params){


        List<CriteriaFilter<CityEntity>> filters = List.of(
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
                CityEntity.class,
                filters,
                params,
                cityMapper::createDtoFromEntity
        );
    }


    public CategoryInstitutionDTO createCategoryInstitution(@RequestBody CategoryInstitutionDTO categoryInstitutionDTO){
        CategoryInstitutionEntity categoryInstitutionEntity = categoryInstitutionMapper.createEntityFromDTO(categoryInstitutionDTO);
        categoryInstitutionRepository.save(categoryInstitutionEntity);
        return(categoryInstitutionMapper.createDtoFromEntity(categoryInstitutionEntity));
    }

    public CategoryInstitutionDTO editCategoryInstitution(@PathVariable("id") Long id, @RequestBody CategoryInstitutionDTO categoryInstitutionDTO){
        CategoryInstitutionEntity categoryInstitutionEntity = categoryInstitutionRepository.findById(id).orElseThrow();

        categoryInstitutionEntity.setName(categoryInstitutionDTO.name());
        categoryInstitutionEntity.setDescription(categoryInstitutionDTO.description());

        categoryInstitutionRepository.save(categoryInstitutionEntity);

        return (categoryInstitutionMapper.createDtoFromEntity(categoryInstitutionEntity));
    }

    public CategoryInstitutionDTO getByIdCategoryInstitution(@PathVariable("id") Long id){
        return (categoryInstitutionMapper.createDtoFromEntity(categoryInstitutionRepository.findById(id).orElseThrow()));
    }

    public String deleteByIdCategoryInstitution(@PathVariable("id") Long id){
        categoryInstitutionRepository.deleteById(id);
        return ("Category Institution with id: '" + id + "' was deleted");
    }

    public PageResult<CategoryInstitutionDTO> searchCategoryOfInstitution(String name, PageParams params){

        List<CriteriaFilter<CategoryInstitutionEntity>> filters = List.of(
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
                CategoryInstitutionEntity.class,
                filters,
                params,
                categoryInstitutionMapper::createDtoFromEntity
        );
    }


    public PageResult<InstitutionResponseDTO> searchInstitution(String name, PageParams params){

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

    public InstitutionResponseDTO createInstitution(InstitutionRequestDTO institutionRequestDTO){

        InstitutionEntity institutionEntity = institutionMapper.createEntityFromDTO(institutionRequestDTO);

        institutionEntity.setCategories(categoryInstitutionRepository.findAllById(institutionRequestDTO.categoryIds()));

        institutionEntity.setManagers(userRepository.findAllById(institutionRequestDTO.managersIds()));

        institutionEntity.setCreatedAt(LocalDateTime.now());

        institutionRepository.save(institutionEntity);

        return institutionMapper.createDtoFromEntity(institutionEntity);

    }

    public InstitutionResponseDTO editInstitution(@PathVariable("id") Long id, @RequestBody InstitutionRequestDTO institutionRequestDTO){
        InstitutionEntity institutionEntity = institutionRepository.findById(id).orElseThrow();

        institutionEntity.setName(institutionRequestDTO.name());
        institutionEntity.setCityId(institutionRequestDTO.cityId());
        institutionEntity.setAddress(institutionRequestDTO.address());
        institutionEntity.setCategories(categoryInstitutionRepository.findAllById(institutionRequestDTO.categoryIds()));
        institutionEntity.setContactNumber(institutionRequestDTO.contactNumber());
        institutionEntity.setManagers(userRepository.findAllById(institutionRequestDTO.managersIds()));

        //todo придумать алгоритм расчета рейтинга на основе отзывов
        institutionEntity.setRating(institutionRequestDTO.rating());

        institutionRepository.save(institutionEntity);

        return(institutionMapper.createDtoFromEntity(institutionEntity));

    }

    public InstitutionResponseDTO getInstitutionById(@PathVariable("id") Long id){
        return(institutionMapper.createDtoFromEntity(institutionRepository.findById(id).orElseThrow()));
    }

    public String deleteInstitutionById(@PathVariable("id") Long id){
        institutionRepository.deleteById(id);
        return ("Institution with id: '" + id + "' was deleted");
    }


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

    public String deleteBoxById(@PathVariable("institutionId") Long institutionId, @PathVariable("boxId") Long boxId){

        if (boxRepository.existsByIdAndInstitutionId(boxId, institutionId)){
            boxRepository.deleteById(boxId);
            return "Deleted";
        }
        return "Not found or access denied";
    }

    //todo editUser
    //todo deleteUser
}
