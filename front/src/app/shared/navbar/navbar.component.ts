import { Component, computed, EventEmitter, HostListener, OnInit, Output, signal, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  isMobile = false;
  @ViewChild('sidenav') sidenav!: MatSidenav;
  currentUrl = signal(this.router.url);
  isAuthPage = computed(() =>
    this.currentUrl().includes('/home') || this.currentUrl().includes('/auth')
  );
  hideNavbar = computed(() => {
    const url = this.currentUrl();
    // Masquer la navbar sur /home, ou sur /auth uniquement en mobile
    return url === '/home' || (url.includes('/auth') && this.isMobile);
  });
  @Output() menuToggle = new EventEmitter<void>();


  constructor(private router: Router) {
    this.router.events.subscribe(() => {
      this.currentUrl.set(this.router.url);
    });
  }

  ngOnInit() {
    this.checkScreenSize();
  }

  closeSidenav() {
    if (this.sidenav) {
      this.sidenav.close();
    }
  }

  @HostListener('window:resize', ['$event'])
  checkScreenSize(event?: Event) {

    this.isMobile = window.innerWidth < 768;
  }
  toggleSidenav() {
    this.menuToggle.emit();
  }

  goToProfile() {
    this.router.navigate(['/profile']);
    if (this.sidenav) {
      this.sidenav.close(); // ✅ Ferme le menu après navigation (mobile)
    }
  }

}
