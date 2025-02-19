import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api'; // URL du backend
  private tokenKey = 'auth_token'; // Clé de stockage du token
  private userSubject = new BehaviorSubject<boolean>(this.hasToken());

  constructor(private http: HttpClient, private router: Router) { }

  // Vérifier si l'utilisateur a un token valide
  private hasToken(): boolean {
    return !!localStorage.getItem(this.tokenKey);
  }

  // Observable pour suivre l'état de connexion
  get isAuthenticated$(): Observable<boolean> {
    return this.userSubject.asObservable();
  }

  // Connexion
  login(credentials: { email: string; password: string }): Observable<any> {
    return this.http.post<{ token: string }>(`${this.apiUrl}/login`, credentials).pipe(
      tap((response) => {
        localStorage.setItem(this.tokenKey, response.token);
        this.userSubject.next(true);
      })
    );
  }

  // Inscription
  register(userData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, userData);
  }

  // Déconnexion
  logout(): void {
    localStorage.removeItem(this.tokenKey);
    this.userSubject.next(false);
    this.router.navigate(['/auth']);
  }

  // Récupérer le token stocké
  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }
}
