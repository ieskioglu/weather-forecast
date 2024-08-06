package com.project.weather_forecast.service;

import com.project.weather_forecast.enumeration.TemperatureType;
import com.project.weather_forecast.mapper.WeatherMapper;
import com.project.weather_forecast.model.City;
import com.project.weather_forecast.model.Weather;
import com.project.weather_forecast.repository.CityRepository;
import com.project.weather_forecast.repository.WeatherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WeatherServiceTest {

    @Mock
    private WeatherRepository weatherRepository;

    @Mock
    private WeatherMapper weatherMapper;

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getWeatherByCityAndDateFound() {
        //Given
        City city = new City();
        city.setId(1);
        LocalDate date = LocalDate.of(2024, 8, 5);

        Weather weather = new Weather();
        weather.setCity(city);
        weather.setFeelsLike(32);
        weather.setTempMax(32);
        weather.setTempMin(32);
        weather.setDate(date);
        weather.setTemp(32);
        weather.setId(1);

        //When
        when(weatherRepository.findByCityIdAndDate(city.getId(),date))
                .thenReturn(Optional.of(weather));

        //Test
        Optional<Weather> response = weatherService.getWeatherByCityAndDate(city,date);

        //Assert
        assertTrue(response.isPresent());
        assertEquals(response.get().getId(),weather.getId());
        assertEquals(response.get().getTempMax(),weather.getTempMax());
        assertEquals(response.get().getTempMin(),weather.getTempMin());
        assertEquals(response.get().getTemp(),weather.getTemp());
        assertEquals(response.get().getDate(),weather.getDate());
        assertEquals(response.get().getCity().getId(),weather.getCity().getId());
        verify(weatherRepository, times(1)).findByCityIdAndDate(city.getId(), date);
    }

    @Test
    public void getWeatherByCityAndDateNotFound() {
        //Given
        City city = new City();
        city.setId(1);
        city.setName("Test");
        LocalDate date = LocalDate.of(2024, 8, 5);

        //When
        when(weatherRepository.findByCityIdAndDate(city.getId(),date))
                .thenReturn(Optional.empty());

        //Test
        Optional<Weather> response = weatherService.getWeatherByCityAndDate(city,date);

        //Assert
        assertFalse(response.isPresent());
        verify(weatherRepository, times(1)).findByCityIdAndDate(city.getId(), date);
    }

    @Test
    void getWeathersByCityIdAndDateRangeCelcius() {
        //Given
        City city = new City();
        city.setId(1);
        TemperatureType temperatureType = TemperatureType.C;
        LocalDate startDate = LocalDate.of(2024, 8, 1);
        LocalDate endDate = LocalDate.of(2024, 8, 5);

        Weather weather1 = new Weather();
        weather1.setCity(city);
        weather1.setFeelsLike(32);
        weather1.setTempMax(32);
        weather1.setTempMin(32);
        weather1.setDate(LocalDate.of(2024, 8, 2));
        weather1.setTemp(32);
        weather1.setId(1);

        Weather weatherCelcius = new Weather();
        weatherCelcius.setCity(city);
        weatherCelcius.setFeelsLike(0);
        weatherCelcius.setTempMax(0);
        weatherCelcius.setTempMin(0);
        weatherCelcius.setDate(LocalDate.of(2024, 8, 2));
        weatherCelcius.setTemp(0);
        weatherCelcius.setId(1);


        List<Weather> weathers = Arrays.asList(weather1);

        //When
        when(weatherRepository.findByCityIdAndDateRange(city.getId(), startDate, endDate)).thenReturn(weathers);
        when(weatherMapper.toCelcius(weather1)).thenReturn(weatherCelcius);

        //Test
        List<Weather> result = weatherService.getWeathersByCityIdAndDateRange(city.getId(), temperatureType, startDate, endDate);

        //Assert
        assertEquals(1, result.size());
        assertEquals(0, result.get(0).getTemp());
        assertEquals(0, result.get(0).getTempMin());
        assertEquals(0, result.get(0).getTempMax());
        assertEquals(0, result.get(0).getFeelsLike());
        verify(weatherRepository, times(1)).findByCityIdAndDateRange(city.getId(), startDate, endDate);
        verify(weatherMapper, times(1)).toCelcius(weather1);
    }

    @Test
    void getWeathersByCityIdAndDateRangeNoConvert() {
        //Given
        City city = new City();
        city.setId(1);
        TemperatureType temperatureType = TemperatureType.F;
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
        when(weatherRepository.findByCityIdAndDateRange(city.getId(), startDate, endDate)).thenReturn(weathers);

        //Test
        List<Weather> result = weatherService.getWeathersByCityIdAndDateRange(city.getId(), temperatureType, startDate, endDate);

        //Assert
        assertEquals(1, result.size());
        assertEquals(32, result.get(0).getTemp());
        assertEquals(32, result.get(0).getTempMin());
        assertEquals(32, result.get(0).getTempMax());
        assertEquals(32, result.get(0).getFeelsLike());
        verify(weatherRepository, times(1)).findByCityIdAndDateRange(city.getId(), startDate, endDate);
    }

    @Test
    void save() {
        //Given
        City city = new City();
        city.setId(1);

        Weather weather = new Weather();
        weather.setCity(city);
        weather.setFeelsLike(32);
        weather.setTempMax(32);
        weather.setTempMin(32);
        weather.setDate(LocalDate.of(2024, 8, 2));
        weather.setTemp(32);
        weather.setId(1);

        //Test
        weatherService.save(weather);

        //Assert
        verify(weatherRepository, times(1)).save(weather);
    }
}