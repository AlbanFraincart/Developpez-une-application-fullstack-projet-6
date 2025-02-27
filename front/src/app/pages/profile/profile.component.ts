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
      email: ['', [Validators.required, Validators.email]]
    });

    this.authService.getCurrentUser().subscribe((user: User) => {
      this.user = user;
      this.profileForm.patchValue({
        username: user.username,
        email: user.email
      });

      // 🔹 Charger les abonnements
      this.subscriptionsService.getUserSubscriptions(user.id).subscribe((subs: Subscription[]) => {
        this.subscriptions = subs;
      });

    });
  }

  toggleEdit() {
    this.isEditing = !this.isEditing;
  }
  saveProfile() {
    if (this.profileForm.invalid) return;

    const updatedUser = {
      username: this.profileForm.value.username,
      email: this.profileForm.value.email
    };

    this.profileService.updateProfile(updatedUser).subscribe((user: User) => {
      this.user = user; // ✅ Met à jour l'utilisateur en local
      this.isEditing = false;
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
