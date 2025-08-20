package com.raksa.app.services.jwt;

import com.raksa.app.dto.AuthResponse;
import com.raksa.app.dto.UserResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.security.Key;

@Service
public class JwtService {

    private final Key key;
    private final int ttlDays;

    public JwtService(
            @Value("${spring.security.oauth2.resourceserver.jwt.secret}") String secret,
            @Value("${spring.security.oauth2.resourceserver.jwt.ttl-days}") int ttlDays) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.ttlDays = ttlDays;
    }

    /** Generate Access Token (short-lived, e.g. 15 min) */
    public String generateAccessToken(UserResponseDto userResponseDto) {
        Instant now = Instant.now();
        return Jwts.builder()
                .claim("id", userResponseDto.getId())
                .setSubject(userResponseDto.getName())
                .claim("providerId", userResponseDto.getProviderId())
                .claim("provider", userResponseDto.getProvider())
                .claim("picture", userResponseDto.getPicture())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(Duration.ofMinutes(15)))) // 15 min
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /** Generate Refresh Token (long-lived, e.g. ttlDays) */
    public String generateRefreshToken(UserResponseDto userResponseDto) {
        Instant now = Instant.now();
        return Jwts.builder()
                .claim("id", userResponseDto.getId())
                .setSubject(userResponseDto.getName())
                .claim("providerId", userResponseDto.getProviderId())
                .claim("provider", userResponseDto.getProvider())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(Duration.ofDays(ttlDays)))) // e.g. 30 days
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /** Parse and validate any token */
    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String validateToken(String token) {
        return parseToken(token).getSubject();
    }

    public Claims extractClaims(String token) {
        return parseToken(token);
    }
}
