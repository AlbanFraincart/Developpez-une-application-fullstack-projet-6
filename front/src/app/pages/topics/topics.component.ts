import { Component, OnInit } from '@angular/core';
import { TopicsService } from 'src/app/services/topics.service';
import { CardData } from 'src/app/models/card.model';
import { SubscriptionsService } from 'src/app/services/subscriptions.service';
import { AuthService } from 'src/app/core/auth.service';
import { User } from 'src/app/core/auth.model';
import { forkJoin } from 'rxjs';
import { Subscription } from 'src/app/models/subscription.model';

@Component({
  selector: 'app-topics',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss']
})
export class TopicsComponent implements OnInit {
  topics: CardData[] = [];
  userSubscriptions = new Set<number>();

  constructor(private topicsService: TopicsService, private authService: AuthService, private subscriptionsService: SubscriptionsService,) { }

  ngOnInit() {
    this.authService.getCurrentUser().subscribe((user: User) => {
      // 🔹 Utilisation de forkJoin pour exécuter les 2 requêtes **en même temps** et attendre leur réponse
      forkJoin({
        subscriptions: this.subscriptionsService.getUserSubscriptions(user.id),
        topics: this.topicsService.getTopics()
      }).subscribe(({ subscriptions, topics }) => {
        // ✅ Remplir userSubscriptions
        this.userSubscriptions = new Set(subscriptions.map((sub: Subscription) => sub.id));
        console.log('userSubscriptions après récupération:', this.userSubscriptions);

        // ✅ Charger les topics après avoir les abonnements
        this.topics = topics.map(topic => ({
          id: topic.id,
          title: topic.name,
          content: topic.description,
          buttonLabel: this.userSubscriptions.has(topic.id) ? "Abonné(e)" : "S'abonner"
        }));

        console.log('Topics après mise à jour:', this.topics);
      });
    });
  }

  subscribeToTopic(topicId: number) {
    this.authService.getCurrentUser().subscribe((user: User) => {
      this.subscriptionsService.suscribeToTopic(topicId, user.id).subscribe(() => {
        this.topics = this.topics.map(topic => {
          if (topic.id === topicId) {
            return {
              ...topic,
              buttonLabel: "Abonné"
            };
          }
          return topic;
        });
      });
    });
  }
}
