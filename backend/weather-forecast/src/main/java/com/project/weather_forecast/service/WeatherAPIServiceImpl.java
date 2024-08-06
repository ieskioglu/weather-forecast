package com.project.weather_forecast.service;

import com.project.weather_forecast.model.City;
import com.project.weather_forecast.model.Weather;
import com.project.weather_forecast.response.DayResponse;
import com.project.weather_forecast.response.WeatherAPIResponse;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class WeatherAPIServiceImpl implements WeatherAPIService {

    private final String API_URL;
    private final String API_KEY;
    private final String DATE_FORMAT;
    private final CityService cityService;
    private final WeatherService weatherService;
    private final RestTemplate restTemplate;

    public WeatherAPIServiceImpl(Environment environment, CityService cityService, WeatherService weatherService, RestTemplate restTemplate) {
        this.API_URL=environment.getProperty("weather.visualcrossing.api.url");
        this.API_KEY=environment.getProperty("weather.visualcrossing.api.key");
        this.restTemplate = restTemplate;
        this.DATE_FORMAT = "yyyy-MM-dd";
        this.cityService = cityService;
        this.weatherService = weatherService;
    }

    public void fetchWeatherData(){
        var cities = cityService.getAllCities();
        String startDate = DateTimeFormatter.ofPattern(DATE_FORMAT).format(LocalDate.now().minusDays(7));
        String endDate = DateTimeFormatter.ofPattern(DATE_FORMAT).format(LocalDate.now().plusDays(7));

        for (City city:cities
             ) {
            String uri = String.format("%s/%s/%s/%s?key=%s&&include=days",API_URL,city.getName(),startDate,endDate,API_KEY);
            var result = restTemplate.getForObject(uri, WeatherAPIResponse.class);
            if(result !=null && result.getDays() != null){
                for (DayResponse day:result.getDays()
                     ) {
                    Weather weather;
                    var existingCity = weatherService.getWeatherByCityAndDate(city,day.getDatetime());
                    if(existingCity.isPresent()){
                       weather = existingCity.get();

                    }else{
                        weather = new Weather();
                        weather.setDate(day.getDatetime());
                        weather.setCity(city);
                    }

                    weather.setFeelsLike(day.getFeelslike());
                    weather.setTempMin(day.getTempmin());
                    weather.setTempMax(day.getTempmax());
                    weather.setTemp(day.getTemp());

                    weatherService.save(weather);
                }
            }
        }
    }
}
