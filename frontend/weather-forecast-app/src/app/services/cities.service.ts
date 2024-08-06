import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CityResponse } from '../models/city-response';
import { environment } from '../../environments/environment';

const API_URL=`${environment.apiUrl}/cities`;

@Injectable({
  providedIn: 'root'
})
export class CitiesService {

  constructor(private http:HttpClient) { }

  getAllCities():Observable<Array<CityResponse>>{
    return this.http.get<Array<CityResponse>>(API_URL);
  }
}
