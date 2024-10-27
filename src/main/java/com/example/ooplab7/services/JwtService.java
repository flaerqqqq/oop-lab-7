package com.example.ooplab7.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface JwtService {

    String generate(UserDetails userDetails);

    String extractUsername(String token);

    List<GrantedAuthority> extractRoles(String token);

    boolean isValid(String token);
}
