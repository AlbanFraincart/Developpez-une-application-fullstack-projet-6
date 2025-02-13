package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.SubscriptionDto;
import com.openclassrooms.mddapi.entities.Topic;
import com.openclassrooms.mddapi.services.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<Void> subscribe(@Valid @RequestBody SubscriptionDto dto) {
        subscriptionService.subscribe(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> unsubscribe(@Valid @RequestBody SubscriptionDto dto) {
        subscriptionService.unsubscribe(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Topic>> getUserSubscriptions(@PathVariable Long userId) {
        return ResponseEntity.ok(subscriptionService.getUserSubscriptions(userId));
    }

    @GetMapping("/{userId}/{topicId}")
    public ResponseEntity<Boolean> isSubscribed(@PathVariable Long userId, @PathVariable Long topicId) {
        return ResponseEntity.ok(subscriptionService.isSubscribed(userId, topicId));
    }
}
