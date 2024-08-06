import { Component, inject, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { CityResponse } from './models/city-response';
import { CitiesService } from './services/cities.service';
import { MatSelectModule } from '@angular/material/select';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { WeathersService } from './services/weathers.service';
import { WeatherResponse } from './models/weather-response';
import { MatInputModule } from '@angular/material/input';
import { TemperatureType } from './enums/temperature-type';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    MatCardModule,
    MatSelectModule,
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatDatepickerModule,
    MatButtonModule,
    MatInputModule,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  providers: [provideNativeDateAdapter()],
})
export class AppComponent implements OnInit {
  cities: Array<CityResponse> = [];

  private citiesService = inject(CitiesService);
  private weathersService = inject(WeathersService);

  selectedCityId: number;
  startDate: Date;
  endDate: Date;
  minDate = this.minusDays(new Date(), 7);
  maxDate = this.plusDays(new Date(), 7);
  weathers: Array<WeatherResponse> = [];
  selectedTemperature = TemperatureType.F;
  temperatureTypes = TemperatureType;
  submittedTemperature = TemperatureType.F;

  ngOnInit(): void {
    this.citiesService.getAllCities().subscribe((res: Array<CityResponse>) => {
      this.cities = res;
    });
  }

  fetchData(e: any) {
    if (!this.selectedCityId && !this.startDate && !this.endDate) {
      return;
    }
    this.weathersService
      .getWeathersByCityIdAndDateRange(
        this.selectedCityId,
        this.selectedTemperature,
        this.startDate,
        this.endDate
      )
      .subscribe((weathers: Array<WeatherResponse>) => {
       this.weathers = weathers;
       this.submittedTemperature = this.selectedTemperature;
      });
  }

  private plusDays(date: Date, days: number): Date {
    const newDate = new Date(date);
    newDate.setDate(date.getDate() + days);
    return newDate;
  }

  private minusDays(date: Date, days: number): Date {
    const newDate = new Date(date);
    newDate.setDate(date.getDate() - days);
    return newDate;
  }
}
