package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.entities.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper permettant la conversion entre l'entité {@link Article} et son DTO {@link ArticleDto}.
 */
@Mapper(componentModel = "spring")
public interface ArticleMapper {

    /** Instance du mapper pour utilisation manuelle si besoin. */
    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

    /**
     * Convertit une entité {@link Article} en DTO {@link ArticleDto}.
     *
     * @param article L'entité Article à convertir.
     * @return Un objet ArticleDto correspondant.
     */
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "topic.id", target = "topicId")
    ArticleDto toDto(Article article);

    /**
     * Convertit un DTO {@link ArticleDto} en entité {@link Article}.
     *
     * @param articleDto Le DTO ArticleDto à convertir.
     * @return Une entité Article correspondante.
     */
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "topicId", target = "topic.id")
    Article toEntity(ArticleDto articleDto);
}
