package com.crois.course.service.AdminServices;

import com.crois.course.dto.CityDTO.CityDTO;
import com.crois.course.entity.CityEntity;
import com.crois.course.exceptions.NotFoundException;
import com.crois.course.mapper.CityMapper;
import com.crois.course.repositories.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminCityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private CityMapper cityMapper;

    @InjectMocks
    private AdminCityService adminCityService;

    private CityDTO testCityDTO;

    @BeforeEach
    public void setUp() {
        testCityDTO = new CityDTO(
                1L,
                "Voronezh",
                "Voronezhskaya oblast"
        );
    }

    @Test
    void createCity_Success() {

        CityEntity newCity = new CityEntity();

        when(cityMapper.createEntityFromDTO(testCityDTO)).thenReturn(newCity);
        when(cityRepository.save(newCity)).thenReturn(newCity);
        when(cityMapper.createDtoFromEntity(newCity)).thenReturn(testCityDTO);

        CityDTO result = adminCityService.createCity(testCityDTO);

        assertNotNull(result);
        assertEquals(testCityDTO, result);

        verify(cityMapper).createEntityFromDTO(testCityDTO);
        verify(cityRepository).save(newCity);
        verify(cityMapper).createDtoFromEntity(newCity);
    }

    @Test
    void editCity_NotExist_Failed() {
        Long id = 1L;

        when(cityRepository.existsById(id)).thenReturn(false);

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> adminCityService.editCity(id, testCityDTO)
        );

        assertEquals("City with id " + id + " not found", exception.getMessage());

        verifyNoInteractions(cityMapper);
        verify(cityRepository, never()).getReferenceById(id);
    }

    @Test
    void editCity_Exist_Success(){
        long id = 1L;

        CityEntity city = new CityEntity();
        city.setId(id);

        when(cityRepository.existsById(id)).thenReturn(true);
        when(cityRepository.getReferenceById(id)).thenReturn(city);

        CityDTO result = adminCityService.editCity(id, testCityDTO);

        assertEquals(testCityDTO, result);

        verify(cityRepository).existsById(id);
        verify(cityRepository).getReferenceById(id);
        verify(cityMapper).updateEntity(city, testCityDTO);
    }

    @Test
    void getCityById_Failed(){
        Long id = 1L;

        when(cityRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> adminCityService.getCityById(id)
        );
    }

    @Test
    void getCityById_Success() {
        Long id = 1L;

        CityEntity cityEntity = new CityEntity();

        when(cityRepository.findById(id)).thenReturn(Optional.of(cityEntity));
        when(cityMapper.createDtoFromEntity(cityEntity)).thenReturn(testCityDTO);

        CityDTO city = adminCityService.getCityById(id);

        assertEquals(testCityDTO, city);

        verify(cityRepository).findById(id);
        verify(cityMapper).createDtoFromEntity(cityEntity);
    }

    @Test
    void deleteCityById_Success() {
        Long id = 1L;

        when(cityRepository.existsById(id)).thenReturn(true);

        Long result = adminCityService.deleteCityById(id);

        assertEquals(id, result);

        verify(cityRepository).existsById(id);
        verify(cityRepository).deleteById(id);
    }

    @Test
    void getAllCities() {
        CityEntity entity = new CityEntity();
        entity.setId(1L);
        entity.setName("Voronezh");
        entity.setRegion("Voronezhskaya oblast");

        CityDTO expectedDto = new CityDTO(
                1L,
                "Voronezh",
                "Voronezhskaya oblast");

        when(cityRepository.findAll()).thenReturn(List.of(entity));
        when(cityMapper.createDtoFromEntity(entity)).thenReturn(expectedDto);

        List<CityDTO> result = adminCityService.getAllCities();

        assertEquals(1, result.size());
        assertEquals(expectedDto.name(), result.get(0).name());
        verify(cityRepository).findAll();
    }
}