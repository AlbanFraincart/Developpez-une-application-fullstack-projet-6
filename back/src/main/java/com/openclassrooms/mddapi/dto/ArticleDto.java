package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleDto {

    private Long id;

    @NotBlank(message = "Le titre est obligatoire")
    @Size(max = 200, message = "Le titre ne doit pas dépasser 200 caractères")
    private String title;

    @NotBlank(message = "Le contenu est obligatoire")
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @NotNull(message = "L'identifiant de l'utilisateur est obligatoire")
    private Long userId;

    private String authorUsername;

    @NotNull(message = "L'identifiant du sujet est obligatoire")
    private Long topicId;

    private String topicName;
    private List<CommentDto> comments;
}
