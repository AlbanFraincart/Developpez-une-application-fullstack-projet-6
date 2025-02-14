package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.entities.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper permettant la conversion entre l'entité {@link Comment} et son DTO {@link CommentDto}.
 */
@Mapper(componentModel = "spring")
public interface CommentMapper {

    /** Instance du mapper pour utilisation manuelle si besoin. */
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    /**
     * Convertit une entité {@link Comment} en DTO {@link CommentDto}.
     *
     * @param comment L'entité Comment à convertir.
     * @return Un objet CommentDto correspondant.
     */
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "article.id", target = "articleId")
    CommentDto toDto(Comment comment);

    /**
     * Convertit un DTO {@link CommentDto} en entité {@link Comment}.
     *
     * @param commentDto Le DTO CommentDto à convertir.
     * @return Une entité Comment correspondante.
     */
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "articleId", target = "article.id")
    Comment toEntity(CommentDto commentDto);
}
