package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;


@Component
@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto, User> {
}
