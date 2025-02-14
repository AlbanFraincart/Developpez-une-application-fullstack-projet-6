package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.entities.Topic;
import org.mapstruct.Mapper;

/**
 * Mapper permettant la conversion entre l'entité {@link Topic} et son DTO {@link TopicDto}.
 */
@Mapper(componentModel = "spring")
public interface TopicMapper extends EntityMapper<TopicDto, Topic> {
}
