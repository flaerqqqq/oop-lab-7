package com.example.ooplab7.services;

import com.example.ooplab7.dtos.JwtResponseDto;
import com.example.ooplab7.dtos.UserLoginRequestDto;
import com.example.ooplab7.dtos.UserRegisterRequestDto;
import com.example.ooplab7.dtos.UserResponseDto;

public interface AuthService {

    UserResponseDto register(UserRegisterRequestDto request);

    JwtResponseDto login(UserLoginRequestDto request);
}

