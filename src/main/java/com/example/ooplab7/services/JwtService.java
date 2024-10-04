package com.example.ooplab7.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String generate(UserDetails userDetails);
}
