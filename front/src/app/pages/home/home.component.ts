import { Component, OnInit } from '@angular/core';
import { HelloService } from 'src/app/services/hello.service';
import { TopicsService } from 'src/app/services/topics.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  constructor(private helloService: HelloService, private topicService: TopicsService) { }

  public helloMessage: string = '';
  public topics: any = [];

  ngOnInit(): void { }

  start() {
    alert('Commencez par lire le README et à vous de jouer !');
  }

  hello() {
    this.helloService.getHello().subscribe((data) => {
      this.helloMessage = data;
    })
  }

  loadTopics() {
    this.topicService.getHello().subscribe((data) => {
      console.log('data', data);
      this.topics = data;
    })
  }
}
