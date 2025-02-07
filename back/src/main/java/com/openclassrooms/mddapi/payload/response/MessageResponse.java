package com.openclassrooms.mddapi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO utilisé pour les messages de réponse (succès ou erreur).
 */
@Data
@AllArgsConstructor
public class MessageResponse {
    private String message;
}
