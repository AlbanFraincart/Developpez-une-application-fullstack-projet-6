package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.SubscriptionDto;
import com.openclassrooms.mddapi.entities.Topic;
import com.openclassrooms.mddapi.entities.User;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.repositories.TopicRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    @Transactional
    public void subscribe(SubscriptionDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        Topic topic = topicRepository.findById(dto.getTopicId())
                .orElseThrow(() -> new ResourceNotFoundException("Topic non trouvé"));

        if (!user.getSubscriptions().contains(topic)) {
            user.getSubscriptions().add(topic);
            userRepository.save(user);
        }
    }

    @Transactional
    public void unsubscribe(SubscriptionDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        Topic topic = topicRepository.findById(dto.getTopicId())
                .orElseThrow(() -> new ResourceNotFoundException("Topic non trouvé"));

        user.getSubscriptions().remove(topic);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<Topic> getUserSubscriptions(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        return user.getSubscriptions();
    }

    @Transactional(readOnly = true)
    public boolean isSubscribed(Long userId, Long topicId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        return user.getSubscriptions().stream().anyMatch(topic -> topic.getId().equals(topicId));
    }
}
