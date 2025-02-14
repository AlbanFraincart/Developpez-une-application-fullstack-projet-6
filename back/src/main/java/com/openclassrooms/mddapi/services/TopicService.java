package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.entities.Topic;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.mapper.TopicMapper;
import com.openclassrooms.mddapi.payload.response.ResponseDto;
import com.openclassrooms.mddapi.repositories.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service gérant les opérations sur les topics.
 */
@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    /**
     * Récupère tous les topics.
     *
     * @return Liste des topics sous forme de DTOs.
     */
    @Transactional(readOnly = true)
    public List<TopicDto> getAllTopics() {
        return topicMapper.toDto(topicRepository.findAll());
    }

    /**
     * Récupère un topic par son ID.
     *
     * @param id Identifiant du topic.
     * @return DTO du topic.
     * @throws ResourceNotFoundException si le topic n'existe pas.
     */
    @Transactional(readOnly = true)
    public TopicDto getTopicById(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Le topic avec l'ID " + id + " n'existe pas."));
        return topicMapper.toDto(topic);
    }

    /**
     * Ajoute un nouveau topic.
     *
     * @param topicDto DTO du topic à créer.
     * @return DTO du topic créé.
     */
    @Transactional
    public TopicDto addTopic(TopicDto topicDto) {
        Topic topic = topicMapper.toEntity(topicDto);
        topic = topicRepository.save(topic);
        return topicMapper.toDto(topic);
    }

    /**
     * Met à jour un topic existant.
     *
     * @param id       Identifiant du topic à mettre à jour.
     * @param topicDto Nouvelles informations du topic.
     * @return DTO du topic mis à jour.
     * @throws ResourceNotFoundException si le topic n'existe pas.
     */
    @Transactional
    public TopicDto updateTopic(Long id, TopicDto topicDto) {
        Topic existingTopic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Le topic avec l'ID " + id + " n'existe pas."));

        existingTopic.setName(topicDto.getName());
        existingTopic.setDescription(topicDto.getDescription());

        return topicMapper.toDto(topicRepository.save(existingTopic));
    }

    /**
     * Supprime un topic par son ID.
     *
     * @param id Identifiant du topic à supprimer.
     * @return Réponse confirmant la suppression.
     * @throws ResourceNotFoundException si le topic n'existe pas.
     */
    @Transactional
    public ResponseDto removeTopic(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Le topic avec l'ID " + id + " n'existe pas."));
        topicRepository.delete(topic);
        return new ResponseDto("Topic supprimé avec succès", true);
    }
}
