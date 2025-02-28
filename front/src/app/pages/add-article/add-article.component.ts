import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/core/auth.model';
import { AuthService } from 'src/app/core/auth.service';
import { ArticlesService } from 'src/app/services/articles.service';
import { TopicsService } from 'src/app/services/topics.service';

@Component({
  selector: 'app-add-article',
  templateUrl: './add-article.component.html',
  styleUrls: ['./add-article.component.scss']
})
export class AddArticleComponent implements OnInit {
  articleForm!: FormGroup;
  topics: { id: number, name: string }[] = [];
  currentUser!: User | null;


  constructor(private fb: FormBuilder, private router: Router, private topicsService: TopicsService, private articlesService: ArticlesService, private authService: AuthService) { }

  ngOnInit() {
    this.articleForm = this.fb.group({
      topic: ['', Validators.required],
      title: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(200)]],
      content: ['', [Validators.required, Validators.minLength(10)]]
    });

    // 🔹 Charger les thèmes disponibles
    this.topicsService.getTopics().subscribe((data) => {
      this.topics = data;
    });

    this.authService.currentUser$.subscribe((user) => {
      this.currentUser = user;
    });
  }

  goBack() {
    this.router.navigate(['/articles']);
  }

  createArticle() {
    if (this.articleForm.invalid || !this.currentUser) return;

    const newArticle = {
      title: this.articleForm.value.title,
      content: this.articleForm.value.content,
      userId: this.currentUser.id, // ✅ Utilisation du cache utilisateur
      topicId: this.articleForm.value.topic
    };

    this.articlesService.createArticle(newArticle).subscribe(() => {
      this.router.navigate(['/articles']);
    }, (error) => {
      console.error("Erreur lors de la création de l'article :", error);
    });
  }
}
