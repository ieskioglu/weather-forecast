package com.project.weather_forecast.mapper;

import com.project.weather_forecast.model.City;
import com.project.weather_forecast.response.CityResponse;
import org.springframework.stereotype.Service;

@Service
public class CityMapper {
    public CityResponse toCityResponse(City city){
        return  new CityResponse(city.getId(),city.getName());
    }
}
