package com.project.weather_forecast.service;

import com.project.weather_forecast.model.City;
import com.project.weather_forecast.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityServiceImpl cityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldGetAllCities() {
        //Given
        City city1 = new City();
        city1.setId(1);
        city1.setName("City1");

        City city2 = new City();
        city2.setId(2);
        city2.setName("City2");

        //When
        when(cityRepository.findAll()).thenReturn(Arrays.asList(city1,city2));

        //Test
        List<City> result = cityService.getAllCities();

        //Asserts
        assertEquals(2,result.size());
        verify(cityRepository,times(1)).findAll();
    }
}