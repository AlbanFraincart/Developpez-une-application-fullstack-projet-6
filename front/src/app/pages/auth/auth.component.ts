import { Component, computed, HostListener, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { RegisterUser } from 'src/app/core/auth.model';
import { AuthService } from 'src/app/core/auth.service';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.scss'
})
export class AuthComponent implements OnInit {
  isMobile = signal(window.innerWidth < 768);
  authForm!: FormGroup;
  authError = signal<string | null>(null);
  // Détecter la valeur de `mode` dans l'URL
  currentMode = signal(this.route.snapshot.queryParams['mode'] || 'login');
  isLoginMode = computed(() => this.currentMode() === 'login');


  constructor(private fb: FormBuilder,
    private authService: AuthService,
    private router: Router, private route: ActivatedRoute) {
    // Met à jour `currentMode` quand l'URL change
    this.route.queryParams.subscribe((params) => {
      this.currentMode.set(params['mode'] || 'login');
      this.updateFormFields();
    });
  }

  ngOnInit() {
    this.createForm();
  }

  createForm() {
    this.authForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });

    this.updateFormFields();
  }

  updateFormFields() {
    if (!this.authForm) return;

    if (!this.isLoginMode()) {
      this.authForm.addControl('username', this.fb.control('', [Validators.required]));
    } else {
      if (this.authForm.contains('username')) {
        this.authForm.removeControl('username');
      }
    }
  }

  onSubmit() {
    if (this.authForm.invalid) return;

    this.authError.set(null);
    const formData = this.authForm.value;

    if (this.isLoginMode()) {
      this.authService.login({ email: formData.email, password: formData.password }).subscribe({
        next: () => this.router.navigate(['/articles']),
        error: (err) => {
          console.error('Erreur de connexion', err);
          this.authError.set(err.message);
        }
      });
    } else {
      const newUser: RegisterUser = {
        email: formData.email,
        password: formData.password,
        username: formData.username,
      };
      this.authService.register(newUser).subscribe({
        next: () => this.router.navigate(['/articles']),
        error: (err) => {
          console.error("Erreur d'inscription", err);
          this.authError.set(err.message);
        }
      });
    }
  }

  back() {
    this.router.navigate(['/']);
  }

  @HostListener('window:resize', [])
  onResize() {
    this.isMobile.set(window.innerWidth < 768);
  }
}
