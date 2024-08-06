package com.project.weather_forecast.repository;

import com.project.weather_forecast.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Weather,Integer> {
    Optional<Weather> findByCityIdAndDate(int cityId, LocalDate date);

    @Query("SELECT w FROM Weather w where w.city.id = :cityId and w.date >= :startDate and date <= :endDate")
    List<Weather> findByCityIdAndDateRange(int cityId,LocalDate startDate,LocalDate endDate);
}
