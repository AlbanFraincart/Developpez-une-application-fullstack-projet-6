package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.entities.Topic;
import com.openclassrooms.mddapi.repositories.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.services.TopicService;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @GetMapping
    public List<TopicDto> getAllTopics() {
        return topicService.getAllTopics();
    }


}
