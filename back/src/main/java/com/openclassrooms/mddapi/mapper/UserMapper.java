package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.entities.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * Mapper permettant la conversion entre l'entité {@link User} et son DTO {@link UserDto}.
 */
@Component
@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto, User> {
}
