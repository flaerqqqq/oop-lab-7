package com.example.ooplab7.services;

import com.example.ooplab7.dtos.UserRegisterRequest;
import com.example.ooplab7.dtos.UserResponseDto;

public interface AuthService {

    UserResponseDto register(UserRegisterRequest request);
}

