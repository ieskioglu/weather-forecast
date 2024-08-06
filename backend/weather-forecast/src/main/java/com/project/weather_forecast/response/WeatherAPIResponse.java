package com.project.weather_forecast.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherAPIResponse {
    private String address;
    private List<DayResponse> days;

    @Override
    public String toString() {
        return "WeatherAPIResponse{" +
                "address='" + address + '\'' +
                ", days=" + days +
                '}';
    }
}
