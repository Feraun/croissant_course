package com.crois.course.service;

import com.crois.course.dto.CityDTO.CityDTO;
import com.crois.course.entity.CityEntity;
import com.crois.course.mapper.CityMapper;
import com.crois.course.repositories.CityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AdminService {

    private final CityMapper cityMapper;
    private final CityRepository cityRepository;

    public AdminService(CityRepository cityRepository, CityMapper cityMapper){
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
    }

    //todo public CityDto getAllCity
    // надо продумать отдельную функцию получения всех записей из какой-либо таблицы, а в параметры передать нужный класс

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
        return ("City with id : '" + id + "' was deleted");
    }

}
