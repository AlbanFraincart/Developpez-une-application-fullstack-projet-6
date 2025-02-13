package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.services.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<ArticleDto> addArticle(@Valid @RequestBody ArticleDto articleDto) {
        return ResponseEntity.ok(articleService.addArticle(articleDto));
    }

    @GetMapping
    public ResponseEntity<List<ArticleDto>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAllArticles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getArticleById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleDto> updateArticle(@PathVariable Long id, @Valid @RequestBody ArticleDto articleDto) {
        return ResponseEntity.ok(articleService.updateArticle(id, articleDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/topic/{topicId}")
    public ResponseEntity<List<ArticleDto>> getArticlesByTopic(@PathVariable Long topicId) {
        return ResponseEntity.ok(articleService.getArticlesByTopic(topicId));
    }

}
