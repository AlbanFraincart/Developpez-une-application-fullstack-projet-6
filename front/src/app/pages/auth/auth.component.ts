import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/core/auth.service';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.scss'
})
export class AuthComponent implements OnInit {
  isLoginMode = true; // Par défaut, mode connexion
  authForm!: FormGroup;



  constructor(private route: ActivatedRoute, private fb: FormBuilder,
    private authService: AuthService,
    private router: Router) { }

  ngOnInit() {
    // this.route.queryParams.subscribe((params) => {
    //   this.isLoginMode = params['mode'] !== 'register'; // Si "register", passe en mode inscription
    // });

    // Création du formulaire
    this.authForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      username: [''],
      firstName: [''],
      lastName: [''],
    });
  }

  toggleMode() {
    this.isLoginMode = !this.isLoginMode;
    console.log('toggleMode', this.isLoginMode);
  }

  onSubmit() {
    if (this.authForm.invalid) return;

    const formData = this.authForm.value;

    if (this.isLoginMode) {
      console.log('login', formData);
      this.authService.login({ email: formData.email, password: formData.password }).subscribe({
        next: () => this.router.navigate(['/dashboard']),
        error: (err) => console.error('Erreur de connexion', err)
      });
    } else {
      console.log('register', formData);
      const newUser = {
        email: formData.email,
        password: formData.password,
        username: formData.username,
        firstName: formData.firstName,
        lastName: formData.lastName,
      };
      this.authService.register(newUser).subscribe({
        next: () => this.router.navigate(['/dashboard']),
        error: (err) => console.error('Erreur d\'inscription', err)
      });
    }
  }
}
