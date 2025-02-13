package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.entities.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "article.id", target = "articleId")
    CommentDto toDto(Comment comment);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "articleId", target = "article.id")
    Comment toEntity(CommentDto commentDto);
}
