import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.scss'
})
export class AuthComponent implements OnInit {
  isLoginMode = true; // Par défaut, mode connexion

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.queryParams.subscribe((params) => {
      this.isLoginMode = params['mode'] !== 'register'; // Si "register", passe en mode inscription
    });
  }

  toggleMode() {
    this.isLoginMode = !this.isLoginMode;
  }

  test() {
    console.log('d')
  }
}
