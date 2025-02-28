import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Article } from '../models/article.model';
import { ArticleComment, ArticleDetails } from '../models/article-details.model';

@Injectable({
  providedIn: 'root'
})
export class ArticlesService {
  private apiUrl = "http://localhost:8080/api";

  constructor(private httpClient: HttpClient) { }

  getArticlesBySubscription(id: number): Observable<Article[]> {
    return this.httpClient.get<Article[]>(`${this.apiUrl}/articles/subscriptions/${id}`);
  }

  createArticle(article: { title: string; content: string; userId: number; topicId: number }): Observable<Article> {
    return this.httpClient.post<Article>(`${this.apiUrl}/articles`, article);
  }


  getArticleDetails(articleId: number): Observable<ArticleDetails> {
    return this.httpClient.get<ArticleDetails>(`${this.apiUrl}/articles/${articleId}`);
  }

  addComment(comment: { content: string; userId: number; articleId: number }): Observable<ArticleComment> {
    return this.httpClient.post<ArticleComment>(`${this.apiUrl}/comments`, comment);
  }

}
