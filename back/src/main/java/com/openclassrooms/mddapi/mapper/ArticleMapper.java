package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.entities.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "topic.id", target = "topicId")
    ArticleDto toDto(Article article);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "topicId", target = "topic.id")
    Article toEntity(ArticleDto articleDto);
}
