package com.example.ooplab7.services.impls;

import com.example.ooplab7.services.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class JwtServiceImpl implements JwtService {

    private final SecretKey key;

    @Value("${jwt.expiration}")
    private long expiration;

    public JwtServiceImpl(@Value("${jwt.secret}") String secret) {
        key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String generate(UserDetails userDetails) {
        Date issuedAt = new Date();
        Date expiredAt = new Date(issuedAt.getTime() + expiration);

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities());

        return Jwts.builder()
                .setIssuedAt(issuedAt)
                .setExpiration(expiredAt)
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }


    public List<GrantedAuthority> extractRoles(String token) {
        Claims claims = extractClaims(token);
        List<Map<String, String>> roles = (List<Map<String, String>>) claims.get("roles");

        // Map each role object to a SimpleGrantedAuthority
        return roles.stream()
                .map(roleMap -> new SimpleGrantedAuthority(roleMap.get("authority")))
                .collect(Collectors.toList());
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isValid(String token) {
        try {
            Claims claims = extractClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
                 | IllegalArgumentException | SignatureException e) {
            throw new RuntimeException(e);
        }
    }

}
