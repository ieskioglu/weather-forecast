package com.project.weather_forecast.controller;

import com.project.weather_forecast.enumeration.TemperatureType;
import com.project.weather_forecast.model.City;
import com.project.weather_forecast.model.Weather;
import com.project.weather_forecast.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WeatherControllerTest {
    private MockMvc mockMvc;

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private WeatherController weatherController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(weatherController).build();
    }

    @Test
    void getWeatherByCity() throws Exception {
        //Given
        City city = new City();
        city.setId(1);

        TemperatureType temperatureType = TemperatureType.C;
        LocalDate startDate = LocalDate.of(2024, 8, 1);
        LocalDate endDate = LocalDate.of(2024, 8, 5);

        Weather weather = new Weather();
        weather.setCity(city);
        weather.setFeelsLike(32);
        weather.setTempMax(32);
        weather.setTempMin(32);
        weather.setDate(LocalDate.of(2024, 8, 2));
        weather.setTemp(32);
        weather.setId(1);

        List<Weather> weathers = Arrays.asList(weather);

        //When
        when(weatherService.getWeathersByCityIdAndDateRange(city.getId(), temperatureType, startDate, endDate)).thenReturn(weathers);

        // Act & Assert
        mockMvc.perform(get("/api/weathers/{cityId}/{temperatureType}/{startDate}/{endDate}", city.getId(), temperatureType, startDate, endDate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].date").value("2024-08-02"))
                .andExpect(jsonPath("$[0].temp").value(32))
                .andExpect(jsonPath("$[0].tempMin").value(32))
                .andExpect(jsonPath("$[0].tempMax").value(32))
                .andExpect(jsonPath("$[0].feelsLike").value(32));

        verify(weatherService, times(1)).getWeathersByCityIdAndDateRange(city.getId(), temperatureType, startDate, endDate);
    }
}