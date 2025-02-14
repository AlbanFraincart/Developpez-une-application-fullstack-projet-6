package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.payload.response.ResponseDto;
import com.openclassrooms.mddapi.services.TopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour gérer les topics.
 * Permet d'ajouter, récupérer, mettre à jour et supprimer des topics.
 */
@RestController
@RequestMapping("/api/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    /**
     * Récupère la liste de tous les topics.
     *
     * @return Liste de tous les topics disponibles.
     */
    @GetMapping
    public ResponseEntity<List<TopicDto>> getAllTopics() {
        return ResponseEntity.ok(topicService.getAllTopics());
    }

    /**
     * Récupère un topic par son ID.
     *
     * @param id L'ID du topic recherché.
     * @return Le topic correspondant à l'ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TopicDto> getTopicById(@PathVariable Long id) {
        return ResponseEntity.ok(topicService.getTopicById(id));
    }

    /**
     * Ajoute un nouveau topic.
     *
     * @param topicDto Les détails du topic à créer.
     * @return Le topic créé.
     */
    @PostMapping
    public ResponseEntity<TopicDto> addTopic(@Valid @RequestBody TopicDto topicDto) {
        return ResponseEntity.ok(topicService.addTopic(topicDto));
    }

    /**
     * Met à jour un topic existant.
     *
     * @param id       L'ID du topic à mettre à jour.
     * @param topicDto Les nouvelles données du topic.
     * @return Le topic mis à jour.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TopicDto> updateTopic(@PathVariable Long id, @Valid @RequestBody TopicDto topicDto) {
        return ResponseEntity.ok(topicService.updateTopic(id, topicDto));
    }

    /**
     * Supprime un topic par son ID.
     *
     * @param id L'ID du topic à supprimer.
     * @return Message de confirmation de suppression.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> removeTopic(@PathVariable Long id) {
        return ResponseEntity.ok(topicService.removeTopic(id));
    }
}
