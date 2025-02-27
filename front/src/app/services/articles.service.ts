import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Article } from '../models/article.model';

@Injectable({
  providedIn: 'root'
})
export class ArticlesService {
  private apiUrl = "http://localhost:8080/api";

  constructor(private httpClient: HttpClient) { }

  getArticlesBySubscription(id: number): Observable<Article[]> {
    return this.httpClient.get<Article[]>(`${this.apiUrl}/articles/subscriptions/${id}`);
  }
}
