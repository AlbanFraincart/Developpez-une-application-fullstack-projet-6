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
    private authService: AuthService) {
  }

  ngOnInit() {
    this.authService.currentUser$.subscribe((user) => {
      if (user) {
        this.articlesService.getArticlesBySubscription(user.id).subscribe((data: Article[]) => {
          this.articles = data.map(article => ({
            id: article.id,
            title: article.title,
            content: article.content,
            createdAt: new Date(article.createdAt),
            authorUsername: article.authorUsername
          }));
          this.articles.sort((a, b) =>
            (b.createdAt?.getTime() ?? 0) - (a.createdAt?.getTime() ?? 0)
          );

        });
      }
    });
  }

  goToCreateArticle() {
    // Navigation vers une page de création (exemple)
    this.router.navigate(['add-article']);
  }

  toggleSortOrder() {
    this.sortAscending = !this.sortAscending;
    if (this.sortAscending) {
      // Du plus vieux au plus récent
      this.articles.sort((a, b) =>
        (b.createdAt?.getTime() ?? 0) - (a.createdAt?.getTime() ?? 0)
      );
    } else {
      // Du plus récent au plus vieux
      this.articles.sort((a, b) =>
        (a.createdAt?.getTime() ?? 0) - (b.createdAt?.getTime() ?? 0)
      );
    }
  }


  goToArticle(articleId: number) {
    // Navigation vers la page détail de l'article
    this.router.navigate(['/detail-article', articleId]);
  }
}
