package com.crois.course.service.AdminServices;

import com.crois.course.dto.CityDTO.CityDTO;
import com.crois.course.entity.CityEntity;
import com.crois.course.exceptions.NotFoundException;
import com.crois.course.mapper.CityMapper;
import com.crois.course.repositories.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminCityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private CityMapper cityMapper;

    @InjectMocks
    private AdminCityService adminCityService;

    @Test
    void createCity() {
    }

    @Test
    void editCityFailed() {
        long id = 1L;

        CityDTO cityDTO = new CityDTO(
                1L,
                "Ramon",
                "Voronezhskaya oblast'"
        );

        when(cityRepository.existsById(id)).thenReturn(false);

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> adminCityService.editCity(id, cityDTO)
        );

        assertEquals("City with id " + id + " not found", exception.getMessage());
    }

    @Test
    void editCitySuccesses(){
        long id = 1L;

        CityDTO cityDTO = new CityDTO(
                1L,
                "Ramon",
                "Voronezhskaya oblast'"
        );

        CityEntity city = new CityEntity();
        city.setId(id);

        when(cityRepository.existsById(id)).thenReturn(true);
        when(cityRepository.getReferenceById(id)).thenReturn(city);

        CityDTO result = adminCityService.editCity(id, cityDTO);

        assertEquals(cityDTO, result);

        verify(cityRepository).existsById(id);
        verify(cityRepository).getReferenceById(id);
        verify(cityMapper).updateEntity(city, cityDTO);
    }

    @Test
    void getCityById() {
    }

    @Test
    void deleteCityById() {
    }

    @Test
    void getAllCities() {
    }
}