package com.project.weather_forecast.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DayResponse {
    private LocalDate datetime;
    private double tempmax;
    private double tempmin;
    private double feelslike;
    private double temp;

    @Override
    public String toString() {
        return "DayResponse{" +
                "datetime=" + datetime +
                ", tempmax=" + tempmax +
                ", tempmin=" + tempmin +
                ", feelslike=" + feelslike +
                ", temp=" + temp +
                '}';
    }
}
