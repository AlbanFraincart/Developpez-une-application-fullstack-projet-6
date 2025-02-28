import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ArticlesService } from 'src/app/services/articles.service';
import { ArticleDetails, ArticleComment } from 'src/app/models/article-details.model';
import { AuthService } from 'src/app/core/auth.service';

@Component({
  selector: 'app-detail-article',
  templateUrl: './detail-article.component.html',
  styleUrls: ['./detail-article.component.scss']
})
export class DetailArticleComponent implements OnInit {
  article: ArticleDetails = {} as ArticleDetails;
  commentForm!: FormGroup;
  userId!: number;
  isLoading = true;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private articlesService: ArticlesService,
    private authService: AuthService
  ) { }

  ngOnInit() {
    const articleId = Number(this.route.snapshot.paramMap.get('id'));

    // ✅ Récupération unique des détails de l'article
    this.articlesService.getArticleDetails(articleId).subscribe({
      next: (data) => {
        this.article = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error("Erreur lors du chargement de l'article :", err);
        this.isLoading = false;
      }
    });

    this.authService.currentUser$.subscribe(user => {
      if (user) {
        this.userId = user.id;
      }
    });

    this.commentForm = this.fb.group({
      content: ['', [Validators.required, Validators.minLength(5)]]
    });
  }

  goBack() {
    this.router.navigate(['/articles']);
  }

  addComment() {
    if (this.commentForm.invalid) return;

    const newComment = {
      content: this.commentForm.value.content,
      userId: this.userId,
      articleId: this.article.id
    };

    this.articlesService.addComment(newComment).subscribe({
      next: (comment: ArticleComment) => {
        this.article.comments.push({
          id: comment.id,
          content: comment.content,
          createdAt: comment.createdAt,
          updatedAt: comment.updatedAt,
          userId: comment.userId,
          authorUsername: 'Moi' // Ajout temporaire pour affichage immédiat
        });
        this.commentForm.reset();
      },
      error: (err) => {
        console.error("Erreur lors de l'ajout du commentaire :", err);
      }
    });
  }
}
