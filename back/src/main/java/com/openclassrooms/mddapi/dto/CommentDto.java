package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private Long id;

    @NotBlank(message = "Le contenu du commentaire est obligatoire")
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @NotNull(message = "L'identifiant de l'utilisateur est obligatoire")
    private Long userId;

    private String authorUsername;

    @NotNull(message = "L'identifiant de l'article est obligatoire")
    private Long articleId;
}
