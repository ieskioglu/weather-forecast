package com.project.weather_forecast.mapper;

import com.project.weather_forecast.model.Weather;
import org.springframework.stereotype.Service;

@Service
public class WeatherMapper {
    public Weather toCelcius(Weather weather){
        weather.setFeelsLike(fahrenheitToCelsius(weather.getFeelsLike()));
        weather.setTemp(fahrenheitToCelsius(weather.getTemp()));
        weather.setTempMin(fahrenheitToCelsius(weather.getTempMin()));
        weather.setTempMax(fahrenheitToCelsius(weather.getTempMax()));

        return weather;
    }

    private double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) / 1.8;
    }
}
