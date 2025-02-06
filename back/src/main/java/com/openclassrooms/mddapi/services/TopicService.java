package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.mapper.TopicMapper;
import com.openclassrooms.mddapi.repositories.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    public List<TopicDto> getAllTopics() {
        return topicMapper.toDto(topicRepository.findAll());
    }

    public TopicDto getTopicById(Long id) {
        return topicMapper.toDto(topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found")));
    }
}

