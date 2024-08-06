package com.project.weather_forecast.controller;

import com.project.weather_forecast.mapper.CityMapper;
import com.project.weather_forecast.model.City;
import com.project.weather_forecast.response.CityResponse;
import com.project.weather_forecast.service.CityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/cities")
public class CityController {
    private final CityService cityService;
    private final CityMapper cityMapper;

    public CityController(CityService cityService, CityMapper cityMapper) {
        this.cityService = cityService;
        this.cityMapper = cityMapper;
    }

    @GetMapping()
    public List<CityResponse> getAllCities(){
        return cityService.getAllCities()
                .stream()
                .map(cityMapper::toCityResponse)
                .toList();
    }
}
