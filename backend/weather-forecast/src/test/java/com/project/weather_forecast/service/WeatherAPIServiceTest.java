package com.project.weather_forecast.service;

import com.project.weather_forecast.model.City;
import com.project.weather_forecast.model.Weather;
import com.project.weather_forecast.response.DayResponse;
import com.project.weather_forecast.response.WeatherAPIResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class WeatherAPIServiceTest {
    @Mock
    private Environment environment;

    @Mock
    private CityService cityService;

    @Mock
    private WeatherService weatherService;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherAPIServiceImpl weatherAPIService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(environment.getProperty("weather.visualcrossing.api.url")).thenReturn("http://www.weatherforecastapi.com");
        when(environment.getProperty("weather.visualcrossing.api.key")).thenReturn("weatherForecastApiKey");
    }

    @Test
    void fetchWeatherData() {
        //Given
        City city = new City();
        city.setId(1);
        city.setName("Test");

        List<City> cities = Arrays.asList(city);
        WeatherAPIResponse apiResponse = new WeatherAPIResponse();

        var day = new DayResponse();
        day.setDatetime(LocalDate.now());
        day.setTemp(32);
        day.setFeelslike(32);
        day.setTempmin(32);
        day.setTempmax(32);

        apiResponse.setDays(Arrays.asList(day));
        apiResponse.setAddress("Test");

        //When
        when(cityService.getAllCities()).thenReturn(cities);
        when(restTemplate.getForObject(anyString(), eq(WeatherAPIResponse.class))).thenReturn(apiResponse);
        when(weatherService.getWeatherByCityAndDate(any(City.class), any(LocalDate.class))).thenReturn(Optional.empty());

        //Test
        weatherAPIService.fetchWeatherData();

        //Assert
        verify(cityService, times(1)).getAllCities();
        verify(restTemplate, times(1)).getForObject(anyString(), eq(WeatherAPIResponse.class));
        verify(weatherService, atLeastOnce()).save(any(Weather.class));
    }

    @Test
    public void fetchWeatherDataExistingWeather() {
        //Given
        City city = new City();
        city.setId(1);
        city.setName("Test");

        List<City> cities = Arrays.asList(city);

        LocalDate date = LocalDate.now();

        WeatherAPIResponse apiResponse = new WeatherAPIResponse();
        var day1 = new DayResponse();
        day1.setDatetime(date);
        day1.setTemp(32);
        day1.setFeelslike(32);
        day1.setTempmin(32);
        day1.setTempmax(32);

        apiResponse.setDays(Arrays.asList(day1));
        apiResponse.setAddress("Test");

        Weather existingWeather = new Weather();
        existingWeather.setDate(date);
        existingWeather.setTemp(32);
        existingWeather.setFeelsLike(32);
        existingWeather.setTempMin(32);
        existingWeather.setTempMax(32);
        existingWeather.setCity(city);
        existingWeather.setId(1);

        when(cityService.getAllCities()).thenReturn(cities);
        when(restTemplate.getForObject(anyString(), eq(WeatherAPIResponse.class))).thenReturn(apiResponse);
        when(weatherService.getWeatherByCityAndDate(any(City.class), any(LocalDate.class))).thenReturn(Optional.of(existingWeather));

        //Test
        weatherAPIService.fetchWeatherData();

        //Assert
        verify(weatherService, atLeastOnce()).save(any(Weather.class));
    }
}