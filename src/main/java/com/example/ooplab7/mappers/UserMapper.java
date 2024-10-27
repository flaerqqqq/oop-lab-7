package com.example.ooplab7.mappers;

import com.example.ooplab7.config.MapperConfig;
import com.example.ooplab7.dtos.UserDto;
import com.example.ooplab7.dtos.UserResponseDto;
import com.example.ooplab7.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface UserMapper {

    @Mapping(source = "disabled", target = "isDisabled")
    UserResponseDto toResponseDto(User user);

    @Mapping(source = "disabled", target = "isDisabled")
    UserResponseDto toResponseDto(UserDto userDto);

    @Mapping(source = "disabled", target = "isDisabled")
    UserDto toDto(User user);


}
