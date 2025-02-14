package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.SubscriptionDto;
import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.entities.Topic;
import com.openclassrooms.mddapi.entities.User;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.mapper.TopicMapper;
import com.openclassrooms.mddapi.payload.response.ResponseDto;
import com.openclassrooms.mddapi.repositories.TopicRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service gérant la gestion des abonnements des utilisateurs aux topics.
 */
@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    /**
     * Permet à un utilisateur de s'abonner à un topic.
     *
     * @param dto DTO contenant l'ID de l'utilisateur et du topic.
     * @return Un message de confirmation.
     * @throws ResourceNotFoundException si l'utilisateur ou le topic n'existe pas.
     */
    @Transactional
    public ResponseDto subscribe(SubscriptionDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        Topic topic = topicRepository.findById(dto.getTopicId())
                .orElseThrow(() -> new ResourceNotFoundException("Topic non trouvé"));

        if (!user.getSubscriptions().contains(topic)) {
            user.getSubscriptions().add(topic);
            userRepository.save(user);
            return new ResponseDto("Abonnement au topic réussi", true);
        }
        return new ResponseDto("Utilisateur déjà abonné à ce topic", false);
    }

    /**
     * Permet à un utilisateur de se désabonner d'un topic.
     *
     * @param dto DTO contenant l'ID de l'utilisateur et du topic.
     * @return Un message de confirmation.
     * @throws ResourceNotFoundException si l'utilisateur ou le topic n'existe pas.
     */
    @Transactional
    public ResponseDto unsubscribe(SubscriptionDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        Topic topic = topicRepository.findById(dto.getTopicId())
                .orElseThrow(() -> new ResourceNotFoundException("Topic non trouvé"));

        if (user.getSubscriptions().contains(topic)) {
            user.getSubscriptions().remove(topic);
            userRepository.save(user);
            return new ResponseDto("Désabonnement réussi", true);
        }
        return new ResponseDto("Utilisateur n'était pas abonné à ce topic", false);
    }

    /**
     * Récupère la liste des topics auxquels un utilisateur est abonné.
     *
     * @param userId Identifiant de l'utilisateur.
     * @return Liste des DTO des topics abonnés.
     * @throws ResourceNotFoundException si l'utilisateur n'existe pas.
     */
    @Transactional(readOnly = true)
    public List<TopicDto> getUserSubscriptions(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        return topicMapper.toDto(user.getSubscriptions());
    }

    /**
     * Vérifie si un utilisateur est abonné à un topic donné.
     *
     * @param userId  Identifiant de l'utilisateur.
     * @param topicId Identifiant du topic.
     * @return `true` si l'utilisateur est abonné, sinon `false`.
     * @throws ResourceNotFoundException si l'utilisateur n'existe pas.
     */
    @Transactional(readOnly = true)
    public boolean isSubscribed(Long userId, Long topicId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        return user.getSubscriptions().stream().anyMatch(topic -> topic.getId().equals(topicId));
    }
}
