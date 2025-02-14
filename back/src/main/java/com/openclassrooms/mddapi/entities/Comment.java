package com.openclassrooms.mddapi.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Représente un commentaire laissé sur un article.
 */
@Entity
@Table(name = "comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"user", "article"})
@EntityListeners(AuditingEntityListener.class) // Active l'audit automatique
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT") // Un commentaire ne peut pas être vide
    private String content;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    /** Auteur du commentaire. */
    @ManyToOne(optional = false) // Un commentaire est toujours lié à un utilisateur
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** Article sur lequel le commentaire est posté. */
    @ManyToOne(optional = false) // Un commentaire est toujours lié à un article
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;
}
