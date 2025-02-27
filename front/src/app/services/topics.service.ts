import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Topic } from '../models/topic.model';

@Injectable({
  providedIn: 'root'
})
export class TopicsService {
  private apiUrl = "http://localhost:8080/api";

  constructor(private http: HttpClient) { }


  getTopics(): Observable<Topic[]> {
    return this.http.get<Topic[]>(this.apiUrl + '/topics');
  }


}
