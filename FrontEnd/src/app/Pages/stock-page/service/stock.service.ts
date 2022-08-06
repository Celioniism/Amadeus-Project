import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class StockService {
  constructor(private _http: HttpClient) {}
  private baseUrl = 'http://localhost:8080/api/';
  loadStocks(): Observable<any> {
    return this._http.get(`${this.baseUrl + 'RetrieveStocks'}`);
  }
}
