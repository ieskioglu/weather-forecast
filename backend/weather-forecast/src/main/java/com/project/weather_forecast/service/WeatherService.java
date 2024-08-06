package com.project.weather_forecast.service;

import com.project.weather_forecast.enumeration.TemperatureType;
import com.project.weather_forecast.model.City;
import com.project.weather_forecast.model.Weather;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WeatherService {
    Optional<Weather> getWeatherByCityAndDate(City city, LocalDate date);
    List<Weather> getWeathersByCityIdAndDateRange(int cityId, TemperatureType temperatureType, LocalDate startDate, LocalDate endDate);
    void save(Weather weather);
}
