package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.entities.Article;
import com.openclassrooms.mddapi.entities.Comment;
import com.openclassrooms.mddapi.entities.User;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.repositories.CommentRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

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

    @Transactional(readOnly = true)
    public List<CommentDto> getAllComments() {
        return commentRepository.findAll().stream()
                .map(commentMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public CommentDto getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commentaire non trouvé"));
        return commentMapper.toDto(comment);
    }

    @Transactional
    public CommentDto updateComment(Long id, CommentDto commentDto) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commentaire non trouvé"));

        existingComment.setContent(commentDto.getContent());

        return commentMapper.toDto(commentRepository.save(existingComment));
    }

    @Transactional
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commentaire non trouvé"));
        commentRepository.delete(comment);
    }
}
