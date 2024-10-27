package com.example.ooplab7.controllers;

import com.example.ooplab7.dtos.JwtResponseDto;
import com.example.ooplab7.dtos.UserLoginRequestDto;
import com.example.ooplab7.dtos.UserRegisterRequestDto;
import com.example.ooplab7.dtos.UserResponseDto;
import com.example.ooplab7.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRegisterRequestDto request) {
        UserResponseDto response = authService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody UserLoginRequestDto request) {
        JwtResponseDto response = authService.login(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
