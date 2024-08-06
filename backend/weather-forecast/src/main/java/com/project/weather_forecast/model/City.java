package com.project.weather_forecast.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class City {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String name;

    @OneToMany(
            mappedBy = "city",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private List<Weather> weathers;
}
