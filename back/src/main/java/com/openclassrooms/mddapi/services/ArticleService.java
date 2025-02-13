package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.entities.Article;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.mapper.ArticleMapper;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    @Transactional
    public ArticleDto addArticle(ArticleDto articleDto) {
        Article article = articleMapper.toEntity(articleDto);
        return articleMapper.toDto(articleRepository.save(article));
    }

    @Transactional(readOnly = true)
    public List<ArticleDto> getAllArticles() {
        return articleRepository.findAll().stream()
                .map(articleMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ArticleDto getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("L'article avec l'ID " + id + " n'existe pas."));
        return articleMapper.toDto(article);
    }

    @Transactional
    public ArticleDto updateArticle(Long id, ArticleDto articleDto) {
        Article existingArticle = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("L'article avec l'ID " + id + " n'existe pas."));

        existingArticle.setTitle(articleDto.getTitle());
        existingArticle.setContent(articleDto.getContent());

        return articleMapper.toDto(articleRepository.save(existingArticle));
    }

    @Transactional
    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("L'article avec l'ID " + id + " n'existe pas."));
        articleRepository.delete(article);
    }

    @Transactional(readOnly = true)
    public List<ArticleDto> getArticlesByTopic(Long topicId) {
        List<Article> articles = articleRepository.findByTopicId(topicId);
        return articles.stream()
                .map(articleMapper::toDto)
                .toList();
    }

}
