package com.crois.course.service.AdminServices;

import com.crois.course.dto.CityDTO.CityDTO;
import com.crois.course.entity.CityEntity;
import com.crois.course.exceptions.NotFoundException;
import com.crois.course.mapper.CityMapper;
import com.crois.course.repositories.CityRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminCityService {

    private final CityMapper cityMapper;
    private final CityRepository cityRepository;

    public CityDTO createCity(CityDTO cityDTO){
        CityEntity cityEntity = cityMapper.createEntityFromDTO(cityDTO);
        cityRepository.save(cityEntity);
        return (cityMapper.createDtoFromEntity(cityEntity));
    }

    @Transactional
    public CityDTO editCity(Long id, CityDTO cityDTO){

        CityEntity city = cityRepository.findById(id) // Один запрос вместо двух
                .orElseThrow(() -> new NotFoundException("City with id " + id + " not found"));

        cityMapper.updateEntity(city, cityDTO);
        cityRepository.save(city);
        return cityDTO;

    }

    public CityDTO getCityById(Long id){
        return cityMapper.createDtoFromEntity(
                cityRepository.findById(id).orElseThrow(
                        ()->new NotFoundException("City with id " + id + " not found"))
        );
    }

    public Long deleteCityById(Long id){
        if (cityRepository.existsById(id)) {
            cityRepository.deleteById(id);
            return id;
        }
        throw new NotFoundException("City with id " + id + " not found");
    }

    public Page<CityDTO> searchCity(String cityName, Long cityId, Pageable pageable){
        return cityRepository.searchCities(cityId, cityName, pageable)
                .map(cityMapper::createDtoFromEntity);
    }

    public List<CityDTO> getAllCities(){
        return cityRepository.findAll()
                .stream()
                .map(cityMapper::createDtoFromEntity)
                .toList();
    }
}
