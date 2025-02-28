package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.entities.Article;
import com.openclassrooms.mddapi.entities.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper permettant la conversion entre l'entité {@link Article} et son DTO {@link ArticleDto}.
 */
@Mapper(componentModel = "spring")
public interface ArticleMapper {

    /** Instance du mapper pour utilisation manuelle si besoin. */
    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "topic.id", target = "topicId")
    @Mapping(source = "user.username", target = "authorUsername")
    @Mapping(source = "topic.name", target = "topicName") // ✅ Ajout du mapping du topicName
    @Mapping(target = "comments", expression = "java(mapComments(article.getComments()))") // ✅ Mapping des commentaires
    ArticleDto toDto(Article article);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "topicId", target = "topic.id")
    Article toEntity(ArticleDto articleDto);

    // ✅ Conversion des commentaires en DTO
    default List<CommentDto> mapComments(List<Comment> comments) {
        if (comments == null) return null;
        return comments.stream()
                .map(comment -> new CommentDto(
                        comment.getId(),
                        comment.getContent(),
                        comment.getCreatedAt(),
                        comment.getUpdatedAt(),
                        comment.getUser().getId(),
                        comment.getUser().getUsername(),
                        comment.getArticle().getId()
                ))
                .collect(Collectors.toList());
    }
}
