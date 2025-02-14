package com.openclassrooms.mddapi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO générique pour les réponses de l'API.
 */
@Data
@AllArgsConstructor
public class ResponseDto {
    private String message;
    private boolean success;
}
