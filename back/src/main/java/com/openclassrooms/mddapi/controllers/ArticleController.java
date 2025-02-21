package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.payload.response.ResponseDto;
import com.openclassrooms.mddapi.services.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des articles.
 * Permet de créer, récupérer, mettre à jour et supprimer des articles.
 */
@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    /**
     * Ajoute un nouvel article.
     *
     * @param articleDto Les détails de l'article à créer.
     * @return L'article créé.
     */
    @PostMapping
    public ResponseEntity<ArticleDto> addArticle(@Valid @RequestBody ArticleDto articleDto) {
        return ResponseEntity.ok(articleService.addArticle(articleDto));
    }

    /**
     * Récupère la liste de tous les articles.
     *
     * @return Liste de tous les articles disponibles.
     */
    @GetMapping
    public ResponseEntity<List<ArticleDto>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAllArticles());
    }

    /**
     * Récupère un article par son ID.
     *
     * @param id L'ID de l'article recherché.
     * @return L'article correspondant à l'ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getArticleById(id));
    }

    /**
     * Met à jour un article existant.
     *
     * @param id         L'ID de l'article à mettre à jour.
     * @param articleDto Les nouvelles données de l'article.
     * @return L'article mis à jour.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ArticleDto> updateArticle(@PathVariable Long id, @Valid @RequestBody ArticleDto articleDto) {
        return ResponseEntity.ok(articleService.updateArticle(id, articleDto));
    }

    /**
     * Supprime un article par son ID.
     *
     * @param id L'ID de l'article à supprimer.
     * @return Un message confirmant la suppression.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteArticle(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.deleteArticle(id));
    }

    /**
     * Récupère les articles appartenant à un topic spécifique.
     *
     * @param topicId L'ID du topic.
     * @return Liste des articles associés au topic.
     */
    @GetMapping("/topic/{topicId}")
    public ResponseEntity<List<ArticleDto>> getArticlesByTopic(@PathVariable Long topicId) {
        return ResponseEntity.ok(articleService.getArticlesByTopic(topicId));
    }

    @GetMapping("/subscriptions/{userId}")
    public ResponseEntity<List<ArticleDto>> getArticlesForUser(@PathVariable Long userId) {
        List<ArticleDto> articles = articleService.getArticlesForUser(userId);
        return ResponseEntity.ok(articles);
    }
}
