package com.project.weather_forecast.mapper;

import com.project.weather_forecast.model.City;
import com.project.weather_forecast.response.CityResponse;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class CityMapperTest {

    @InjectMocks
    private CityMapper cityMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldCityToCityResponse(){
        //Given
        City city = new City();
        city.setId(1);
        city.setName("Test");

        //Test
        CityResponse cityResponse = cityMapper.toCityResponse(city);

        //Assert
        assertEquals(cityResponse.id(),city.getId());
        assertEquals(cityResponse.name(),city.getName());
    }
}