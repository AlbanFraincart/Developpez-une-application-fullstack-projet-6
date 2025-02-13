package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.entities.Topic;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.mapper.TopicMapper;
import com.openclassrooms.mddapi.repositories.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    @Transactional(readOnly = true)
    public List<TopicDto> getAllTopics() {
        return topicMapper.toDto(topicRepository.findAll());
    }

    @Transactional(readOnly = true)
    public TopicDto getTopicById(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Le topic avec l'ID " + id + " n'existe pas."));
        return topicMapper.toDto(topic);
    }

    @Transactional
    public TopicDto addTopic(TopicDto topicDto) {
        Topic topic = topicMapper.toEntity(topicDto);
        topic = topicRepository.save(topic);
        return topicMapper.toDto(topic);
    }

    @Transactional
    public TopicDto updateTopic(Long id, TopicDto topicDto) {
        Topic existingTopic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Le topic avec l'ID " + id + " n'existe pas."));

        existingTopic.setName(topicDto.getName());
        existingTopic.setDescription(topicDto.getDescription());

        return topicMapper.toDto(topicRepository.save(existingTopic));
    }

    @Transactional
    public void removeTopic(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Le topic avec l'ID " + id + " n'existe pas."));
        topicRepository.delete(topic);
    }
}
