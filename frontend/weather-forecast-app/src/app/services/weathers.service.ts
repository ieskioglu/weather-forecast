import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { WeatherResponse } from '../models/weather-response';
import { Observable } from 'rxjs';
import { TemperatureType } from '../enums/temperature-type';

const API_URL = `${environment.apiUrl}/weathers`;

@Injectable({
  providedIn: 'root',
})
export class WeathersService {
  constructor(private http: HttpClient) {}

  getWeathersByCityIdAndDateRange(
    cityId: number,
    selectedTemperature:TemperatureType,
    startDate: Date,
    endDate: Date
  ): Observable<Array<WeatherResponse>> {
    const formattedStartDate = `${startDate.getFullYear()}-${String(
      startDate.getMonth() + 1
    ).padStart(2, '0')}-${String(startDate.getDate()).padStart(2, '0')}`;
    const formattedEndDate = `${endDate.getFullYear()}-${String(
      endDate.getMonth() + 1
    ).padStart(2, '0')}-${String(endDate.getDate()).padStart(2, '0')}`;
    return this.http.get<Array<WeatherResponse>>(
      `${API_URL}/${cityId}/${TemperatureType[selectedTemperature]}/${formattedStartDate}/${formattedEndDate}`
    );
  }
}
