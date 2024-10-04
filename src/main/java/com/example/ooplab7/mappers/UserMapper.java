package com.example.ooplab7.mappers;

import com.example.ooplab7.config.MapperConfig;
import com.example.ooplab7.dtos.UserResponseDto;
import com.example.ooplab7.models.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {

    UserResponseDto toResponseDto(User user);
}
