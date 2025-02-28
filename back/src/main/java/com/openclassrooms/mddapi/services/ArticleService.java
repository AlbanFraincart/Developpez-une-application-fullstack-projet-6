package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.entities.Article;
import com.openclassrooms.mddapi.entities.Topic;
import com.openclassrooms.mddapi.entities.User;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.mapper.ArticleMapper;
import com.openclassrooms.mddapi.payload.response.ResponseDto;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.repositories.TopicRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service gérant la logique métier des articles.
 */
@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    /**
     * Ajoute un nouvel article.
     *
     * @param articleDto DTO contenant les informations de l'article.
     * @return DTO de l'article ajouté.
     * @throws ResourceNotFoundException si l'utilisateur ou le topic n'existe pas.
     */
    @Transactional
    public ArticleDto addArticle(ArticleDto articleDto) {
        User user = userRepository.findById(articleDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        Topic topic = topicRepository.findById(articleDto.getTopicId())
                .orElseThrow(() -> new ResourceNotFoundException("Topic non trouvé"));

        Article article = articleMapper.toEntity(articleDto);
        article.setUser(user);
        article.setTopic(topic);

        return articleMapper.toDto(articleRepository.save(article));
    }

    /**
     * Récupère tous les articles existants.
     *
     * @return Liste des DTO des articles.
     */
    @Transactional(readOnly = true)
    public List<ArticleDto> getAllArticles() {
        return articleRepository.findAll().stream()
                .map(articleMapper::toDto)
                .toList();
    }

    /**
     * Récupère un article spécifique par son ID.
     *
     * @param id Identifiant de l'article.
     * @return DTO de l'article.
     * @throws ResourceNotFoundException si l'article n'existe pas.
     */
    @Transactional(readOnly = true)
    public ArticleDto getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("L'article avec l'ID " + id + " n'existe pas."));
        return articleMapper.toDto(article);
    }


    /**
     * Met à jour un article existant.
     *
     * @param id         Identifiant de l'article à mettre à jour.
     * @param articleDto DTO contenant les nouvelles informations.
     * @return DTO de l'article mis à jour.
     * @throws ResourceNotFoundException si l'article n'existe pas.
     */
    @Transactional
    public ArticleDto updateArticle(Long id, ArticleDto articleDto) {
        Article existingArticle = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("L'article avec l'ID " + id + " n'existe pas."));

        existingArticle.setTitle(articleDto.getTitle());
        existingArticle.setContent(articleDto.getContent());

        return articleMapper.toDto(articleRepository.save(existingArticle));
    }

    /**
     * Supprime un article par son ID.
     *
     * @param id Identifiant de l'article à supprimer.
     * @return Un DTO de réponse indiquant si la suppression a réussi.
     * @throws ResourceNotFoundException si l'article n'existe pas.
     */
    @Transactional
    public ResponseDto deleteArticle(Long id) {
        if (!articleRepository.existsById(id)) {
            throw new ResourceNotFoundException("L'article avec l'ID " + id + " n'existe pas.");
        }

        articleRepository.deleteById(id);
        return new ResponseDto("Article supprimé avec succès", true);
    }

    /**
     * Récupère les articles associés à un topic spécifique.
     *
     * @param topicId Identifiant du topic.
     * @return Liste des DTO des articles du topic.
     */
    @Transactional(readOnly = true)
    public List<ArticleDto> getArticlesByTopic(Long topicId) {
        List<Article> articles = articleRepository.findByTopicId(topicId);
        return articles.stream()
                .map(articleMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ArticleDto> getArticlesForUser(Long userId) {
        List<Article> articles = articleRepository.findArticlesByUserSubscriptions(userId);
        return articles.stream().map(articleMapper::toDto).toList();
    }

}
