import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/core/auth.model';
import { AuthService } from 'src/app/core/auth.service';
import { Article } from 'src/app/models/article.model';
import { CardData } from 'src/app/models/card.model';
import { ArticlesService } from 'src/app/services/articles.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {
  articles: CardData[] = [];


  sortAscending = true;

  constructor(private router: Router, private articlesService: ArticlesService,
    private authService: AuthService) { }

  ngOnInit() {
    this.authService.getCurrentUser().subscribe((user: User) => {
      this.articlesService.getArticlesBySubscription(user.id).subscribe((data: Article[]) => {
        this.articles = data.map(article => ({
          ...article,
          id: article.id,
          title: article.title,
          content: article.content,
          createdAt: new Date(article.createdAt).toLocaleDateString('fr-FR'),
          authorUsername: article.authorUsername
        }));
      });
    });
  }

  goToCreateArticle() {
    // Navigation vers une page de création (exemple)
    this.router.navigate(['/articles/new']);
  }

  toggleSortOrder() {
    this.sortAscending = !this.sortAscending;
    // Ex. simple de tri : inverser l'ordre des articles
    this.articles.reverse();
  }

  goToArticle(articleId: number) {
    // Navigation vers la page détail de l'article
    this.router.navigate(['/articles', articleId]);
  }
}
