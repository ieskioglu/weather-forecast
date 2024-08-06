package com.project.weather_forecast.controller;

import com.project.weather_forecast.mapper.CityMapper;
import com.project.weather_forecast.model.City;
import com.project.weather_forecast.response.CityResponse;
import com.project.weather_forecast.service.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CityControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CityService cityService;

    @Mock
    private CityMapper cityMapper;

    @InjectMocks
    private CityController cityController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cityController).build();
    }

    @Test
    void getAllCities() throws Exception {
        //Given
        City city = new City();
        city.setId(1);
        city.setName("Test");

        List<City> cities = Arrays.asList(city);

        CityResponse cityResponse = new CityResponse(1, "Test");
        List<CityResponse> cityResponses = Arrays.asList(cityResponse);

        //When
        when(cityService.getAllCities()).thenReturn(cities);
        when(cityMapper.toCityResponse(city)).thenReturn(cityResponse);

        //Test
        mockMvc.perform(get("/api/cities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test"));

        verify(cityService, times(1)).getAllCities();
        verify(cityMapper, times(1)).toCityResponse(city);
    }
}