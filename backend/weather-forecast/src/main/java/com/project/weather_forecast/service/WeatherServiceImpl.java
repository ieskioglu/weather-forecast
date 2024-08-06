package com.project.weather_forecast.service;

import com.project.weather_forecast.enumeration.TemperatureType;
import com.project.weather_forecast.mapper.WeatherMapper;
import com.project.weather_forecast.model.City;
import com.project.weather_forecast.model.Weather;
import com.project.weather_forecast.repository.WeatherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WeatherServiceImpl implements WeatherService{
    private final WeatherRepository weatherRepository;
    private final WeatherMapper weatherMapper;

    public WeatherServiceImpl(WeatherRepository weatherRepository, WeatherMapper weatherMapper) {
        this.weatherRepository = weatherRepository;
        this.weatherMapper = weatherMapper;
    }

    public Optional<Weather> getWeatherByCityAndDate(City city, LocalDate date) {
        return weatherRepository.findByCityIdAndDate(city.getId(),date);
    }

    public List<Weather> getWeathersByCityIdAndDateRange(int cityId, TemperatureType temperatureType, LocalDate startDate, LocalDate endDate) {
        var weathers = weatherRepository.findByCityIdAndDateRange(cityId,startDate,endDate);
        if(temperatureType == TemperatureType.C){
            weathers = weathers
                    .stream()
                    .map(weatherMapper::toCelcius)
                    .toList();
        }

        return weathers;
    }

    public void save(Weather weather) {
        weatherRepository.save(weather);
    }
}
