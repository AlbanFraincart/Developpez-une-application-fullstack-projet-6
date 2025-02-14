package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.entities.Article;
import com.openclassrooms.mddapi.entities.Comment;
import com.openclassrooms.mddapi.entities.User;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.payload.response.ResponseDto;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.repositories.CommentRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service gérant la logique métier des commentaires.
 */
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    /**
     * Ajoute un commentaire à un article.
     *
     * @param commentDto DTO contenant les informations du commentaire.
     * @return DTO du commentaire ajouté.
     * @throws ResourceNotFoundException si l'utilisateur ou l'article n'existe pas.
     */
    @Transactional
    public CommentDto addComment(CommentDto commentDto) {
        User user = userRepository.findById(commentDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        Article article = articleRepository.findById(commentDto.getArticleId())
                .orElseThrow(() -> new ResourceNotFoundException("Article non trouvé"));

        Comment comment = commentMapper.toEntity(commentDto);
        comment.setUser(user);
        comment.setArticle(article);

        return commentMapper.toDto(commentRepository.save(comment));
    }

    /**
     * Récupère tous les commentaires existants.
     *
     * @return Liste des DTO de commentaires.
     */
    @Transactional(readOnly = true)
    public List<CommentDto> getAllComments() {
        return commentRepository.findAll().stream()
                .map(commentMapper::toDto)
                .toList();
    }

    /**
     * Récupère un commentaire spécifique par son ID.
     *
     * @param id Identifiant du commentaire.
     * @return DTO du commentaire.
     * @throws ResourceNotFoundException si le commentaire n'existe pas.
     */
    @Transactional(readOnly = true)
    public CommentDto getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commentaire non trouvé"));
        return commentMapper.toDto(comment);
    }

    /**
     * Met à jour un commentaire existant.
     *
     * @param id         Identifiant du commentaire à mettre à jour.
     * @param commentDto DTO contenant les nouvelles informations.
     * @return DTO du commentaire mis à jour.
     * @throws ResourceNotFoundException si le commentaire n'existe pas.
     */
    @Transactional
    public CommentDto updateComment(Long id, CommentDto commentDto) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commentaire non trouvé"));

        existingComment.setContent(commentDto.getContent());

        return commentMapper.toDto(commentRepository.save(existingComment));
    }

    /**
     * Supprime un commentaire par son ID.
     *
     * @param id Identifiant du commentaire à supprimer.
     * @return Un DTO de réponse indiquant si la suppression a réussi.
     * @throws ResourceNotFoundException si le commentaire n'existe pas.
     */
    @Transactional
    public ResponseDto deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Le commentaire avec l'ID " + id + " n'existe pas.");
        }

        commentRepository.deleteById(id);
        return new ResponseDto("Commentaire supprimé avec succès", true);
    }
}
