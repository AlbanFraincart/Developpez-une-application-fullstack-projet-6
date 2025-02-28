import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/core/auth.service';
// import { ProfileService } from 'src/app/services/profile.service';
import { SubscriptionsService } from 'src/app/services/subscriptions.service';
import { User } from 'src/app/core/auth.model';
import { Router } from '@angular/router';
import { ProfileService } from 'src/app/services/profile.service';
import { Subscription } from 'src/app/models/subscription.model';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  user!: User;
  profileForm!: FormGroup;
  subscriptions: Subscription[] = [];
  isEditing = false;

  constructor(
    private authService: AuthService,
    private profileService: ProfileService,
    private subscriptionsService: SubscriptionsService,
    private fb: FormBuilder,
    private router: Router
  ) { }

  ngOnInit() {
    // 🔹 Initialisation AVANT l'appel API pour éviter l'erreur
    this.profileForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['']
    });

    this.authService.currentUser$.subscribe((user) => {
      if (user) {
        this.user = user;
        this.profileForm.patchValue({
          username: user.username,
          email: user.email
        });

        this.subscriptionsService.getUserSubscriptions(user.id).subscribe((subs: Subscription[]) => {
          this.subscriptions = subs;
        });
      }
    });
  }

  toggleEdit() {
    this.isEditing = !this.isEditing;
  }

  // email entraîne une déconnexion, password facultatif
  saveProfile() {
    if (this.profileForm.invalid) return;

    const updatedUser = {
      username: this.profileForm.value.username,
      email: this.profileForm.value.email,
      password: this.profileForm.value.password || null // ✅ On n'envoie pas un champ vide
    };

    const emailChanged = updatedUser.email !== this.user.email;

    this.profileService.updateProfile(updatedUser).subscribe({
      next: (user: User) => {
        this.user = user;
        this.isEditing = false;

        if (emailChanged) {
          this.logout();
        }
      },
      error: (err) => {
        console.error("Erreur lors de la mise à jour du profil :", err);
      }
    });
  }


  logout() {
    this.authService.logout();
    this.router.navigate(['/auth']);
  }

  unsubscribe(topicId: number) {
    this.subscriptionsService.unsubscribeFromTopic(this.user.id, topicId).subscribe(() => {
      this.subscriptions = this.subscriptions.filter(sub => sub.id !== topicId);
    });
  }
}