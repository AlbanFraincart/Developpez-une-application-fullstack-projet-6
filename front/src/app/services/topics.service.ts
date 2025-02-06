import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TopicsService {
  private apiUrl = "http://localhost:8080/api";

  constructor(private http: HttpClient) { }


  getHello(): Observable<any> {
    return this.http.get(this.apiUrl + '/topics');
  }
}
