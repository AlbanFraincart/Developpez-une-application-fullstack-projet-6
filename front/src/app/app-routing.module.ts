import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { AuthComponent } from './pages/auth/auth.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { authGuard } from './core/auth.guard';
import { TopicsComponent } from './pages/topics/topics.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { AddArticleComponent } from './pages/add-article/add-article.component';
import { DetailArticleComponent } from './pages/detail-article/detail-article.component';

const routes: Routes = [{ path: 'home', component: HomeComponent },
{ path: 'auth', component: AuthComponent },
{ path: 'articles', component: DashboardComponent, canActivate: [authGuard] }, // Route privée
{ path: 'topics', component: TopicsComponent, canActivate: [authGuard] }, // Route privée
{ path: 'profile', component: ProfileComponent, canActivate: [authGuard] }, // Route privée
{ path: 'add-article', component: AddArticleComponent, canActivate: [authGuard] }, // Route privée
{ path: 'detail-article/:id', component: DetailArticleComponent, canActivate: [authGuard] }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
