package com.example.ooplab7.config;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.NullValueMappingStrategy;

@org.mapstruct.MapperConfig(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public class MapperConfig {
}
