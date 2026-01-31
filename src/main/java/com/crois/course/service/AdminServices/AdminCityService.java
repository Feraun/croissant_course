package com.crois.course.service.AdminServices;

import com.crois.course.dto.CityDTO.CityDTO;
import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
import com.crois.course.entity.CityEntity;
import com.crois.course.mapper.CityMapper;
import com.crois.course.repositories.CityRepository;
import com.crois.course.service.SearchService.CriteriaFilter;
import com.crois.course.service.SearchService.CriteriaSearchUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminCityService {

    private final CityMapper cityMapper;
    private final CityRepository cityRepository;

    @PersistenceContext
    private EntityManager em;

    public CityDTO createCity(@RequestBody CityDTO cityDTO){
        CityEntity cityEntity = cityMapper.createEntityFromDTO(cityDTO);
        cityRepository.save(cityEntity);
        return (cityMapper.createDtoFromEntity(cityEntity));
    }

    public CityDTO editCity(@PathVariable("id") Long id, @RequestBody CityDTO cityDTO){
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

    public Long deleteCityById(@PathVariable("id") Long id){
        cityRepository.deleteById(id);
        return (id);
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

    public List<CityDTO> getAllCities(){
        return cityRepository.findAll()
                .stream()
                .map(cityMapper::createDtoFromEntity)
                .toList();
    }

}
