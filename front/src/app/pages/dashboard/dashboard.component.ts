import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {
  // Simulation d'articles
  articles = [
    {
      id: 1,
      title: 'Titre de l\'article 1',
      date: '01/02/2025',
      author: 'Alice',
      description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.Lorem ipsum dolor sit amet, consectetur adipiscing elit.Lorem ipsum dolor sit amet, consectetur adipiscing elit.Lorem ipsum dolor sit amet, consectetur adipiscing elit.Lorem ipsum dolor sit amet, consectetur adipiscing elit.Lorem ipsum dolor sit amet, consectetur adipiscing elit.Lorem ipsum dolor sit amet, consectetur adipiscing elit.Lorem ipsum dolor sit amet, consectetur adipiscing elit.Lorem ipsum dolor sit amet, consectetur adipiscing elit.Lorem ipsum dolor sit amet, consectetur adipiscing elit.Lorem ipsum dolor sit amet, consectetur adipiscing elit.Lorem ipsum dolor sit amet, consectetur adipiscing elit.Lorem ipsum dolor sit amet, consectetur adipiscing elit.'
    },
    {
      id: 2,
      title: 'Titre de l\'article 2',
      date: '05/02/2025',
      author: 'Bob',
      description: 'Suspendisse potenti. Nullam commodo libero in lorem laoreet.'
    },
    {
      id: 3,
      title: 'Titre de l\'article 3',
      date: '10/02/2025',
      author: 'Charlie',
      description: 'Fusce a metus id ligula ultricies ultrices non quis ligula.'
    }
  ];

  sortAscending = true;

  constructor(private router: Router) { }

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
