package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.SubscriptionDto;
import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.payload.response.ResponseDto;
import com.openclassrooms.mddapi.services.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST gérant les abonnements des utilisateurs aux topics.
 */
@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    /**
     * Permet à un utilisateur de s'abonner à un topic.
     *
     * @param dto DTO contenant les informations de l'abonnement.
     * @return Une réponse confirmant l'abonnement.
     */
    @PostMapping
    public ResponseEntity<ResponseDto> subscribe(@Valid @RequestBody SubscriptionDto dto) {
        return ResponseEntity.ok(subscriptionService.subscribe(dto));
    }

    /**
     * Permet à un utilisateur de se désabonner d'un topic.
     *
     * @param dto DTO contenant les informations de l'abonnement à supprimer.
     * @return Une réponse confirmant le désabonnement.
     */
    @DeleteMapping
    public ResponseEntity<ResponseDto> unsubscribe(@Valid @RequestBody SubscriptionDto dto) {
        return ResponseEntity.ok(subscriptionService.unsubscribe(dto));
    }

    /**
     * Récupère la liste des topics auxquels un utilisateur est abonné.
     *
     * @param userId L'ID de l'utilisateur.
     * @return La liste des topics auxquels il est abonné.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<TopicDto>> getUserSubscriptions(@PathVariable Long userId) {
        return ResponseEntity.ok(subscriptionService.getUserSubscriptions(userId));
    }

    /**
     * Vérifie si un utilisateur est abonné à un topic spécifique.
     *
     * @param userId  L'ID de l'utilisateur.
     * @param topicId L'ID du topic.
     * @return `true` si l'utilisateur est abonné, sinon `false`.
     */
    @GetMapping("/{userId}/{topicId}")
    public ResponseEntity<Boolean> isSubscribed(@PathVariable Long userId, @PathVariable Long topicId) {
        return ResponseEntity.ok(subscriptionService.isSubscribed(userId, topicId));
    }
}
