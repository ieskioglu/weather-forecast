package com.project.weather_forecast.mapper;

import com.project.weather_forecast.model.City;
import com.project.weather_forecast.model.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class WeatherMapperTest {

    @InjectMocks
    private WeatherMapper weatherMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldWeatherTemperatureFahrenheitToCelcius(){
        //Given
        City city =new City();
        city.setId(1);

        Weather weather = new Weather();
        weather.setTempMax(32);
        weather.setTempMin(32);
        weather.setTemp(32);
        weather.setFeelsLike(32);

        Weather weatherCelcius = new Weather();
        weatherCelcius.setTempMax(0);
        weatherCelcius.setTempMin(0);
        weatherCelcius.setTemp(0);
        weatherCelcius.setFeelsLike(0);

        //Test
        Weather response = weatherMapper.toCelcius(weather);

        //Assert
        assertEquals(response.getFeelsLike(),weatherCelcius.getFeelsLike());
        assertEquals(response.getTemp(),weatherCelcius.getTemp());
        assertEquals(response.getTempMax(),weatherCelcius.getTempMax());
        assertEquals(response.getTempMin(),weatherCelcius.getTempMin());
    }
}