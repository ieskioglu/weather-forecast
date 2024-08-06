package com.project.weather_forecast.controller;

import com.project.weather_forecast.enumeration.TemperatureType;
import com.project.weather_forecast.model.Weather;
import com.project.weather_forecast.service.CityService;
import com.project.weather_forecast.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/weathers/{cityId}")
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("{temperatureType}/{startDate}/{endDate}")
    public List<Weather> getWeatherByCity(@PathVariable int cityId, @PathVariable TemperatureType temperatureType, @PathVariable LocalDate startDate, @PathVariable LocalDate endDate){
        return weatherService.getWeathersByCityIdAndDateRange(cityId,temperatureType,startDate,endDate);
    }
}
