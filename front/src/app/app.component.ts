import { Component, computed, HostListener, signal } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  currentUrl = signal(this.router.url);
  isHome = computed(() => this.currentUrl() === '/');
  isMobile = window.innerWidth < 768;

  @HostListener('window:resize', ['$event'])
  onResize() {
    this.isMobile = window.innerWidth < 768;
  }

  constructor(private router: Router) {
    this.router.events.subscribe(() => {
      this.currentUrl.set(this.router.url);
    });
  }

  goToProfile() {
    this.router.navigate(['/profile']);
  }
}
