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
    this.authService.currentUser$.subscribe((user) => {
      if (user) {
        forkJoin({
          subscriptions: this.subscriptionsService.getUserSubscriptions(user.id),
          topics: this.topicsService.getTopics()
        }).subscribe(({ subscriptions, topics }) => {
          this.userSubscriptions = new Set(subscriptions.map((sub: Subscription) => sub.id));

          this.topics = topics.map(topic => ({
            id: topic.id,
            title: topic.name,
            content: topic.description,
            buttonLabel: this.userSubscriptions.has(topic.id) ? "Abonné(e)" : "S'abonner"
          }));
        });
      }
    });
  }


  subscribeToTopic(topicId: number) {
    this.authService.currentUser$.subscribe((user) => {
      if (!user) return;

      this.subscriptionsService.suscribeToTopic(topicId, user.id).subscribe(() => {
        // 🔹 Met à jour la liste des abonnements en local
        this.userSubscriptions.add(topicId);

        this.topics = this.topics.map(topic => {
          if (topic.id === topicId) {
            return {
              ...topic,
              buttonLabel: "Abonné(e)"
            };
          }
          return topic;
        });
      });
    });
  }

}
