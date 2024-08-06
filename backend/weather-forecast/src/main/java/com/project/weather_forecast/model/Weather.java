package com.project.weather_forecast.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Weather {
    @Id
    @GeneratedValue
    private int id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate date;

    private double tempMax;

    private double tempMin;

    private double temp;

    private double feelsLike;

    @ManyToOne
    @JoinColumn(name = "city_id",nullable = false)
    @JsonBackReference
    private City city;
}
