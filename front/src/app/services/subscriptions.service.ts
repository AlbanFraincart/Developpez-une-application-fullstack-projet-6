import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Subscription } from '../models/subscription.model';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionsService {
  private apiUrl = "http://localhost:8080/api";

  constructor(private httpClient: HttpClient) { }

  getUserSubscriptions(userId: number): Observable<Subscription[]> {
    return this.httpClient.get<Subscription[]>(`${this.apiUrl}/subscriptions/${userId}`);
  }

  suscribeToTopic(topicId: number, userId: number): Observable<void> {
    return this.httpClient.post<void>(`${this.apiUrl}/subscriptions`, { topicId, userId });
  }

  unsubscribeFromTopic(userId: number, topicId: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiUrl}/subscriptions`, {
      body: { userId, topicId }
    });
  }
}
