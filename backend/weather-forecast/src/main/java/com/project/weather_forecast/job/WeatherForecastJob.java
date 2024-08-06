package com.project.weather_forecast.job;

import com.project.weather_forecast.service.WeatherAPIService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WeatherForecastJob {
    private final WeatherAPIService weatherAPIService;

    public WeatherForecastJob(WeatherAPIService weatherAPIService) {
        this.weatherAPIService = weatherAPIService;
    }

    @Scheduled(cron="0 0 0 * * *")
    public void execute(){
        weatherAPIService.fetchWeatherData();
    }
}
