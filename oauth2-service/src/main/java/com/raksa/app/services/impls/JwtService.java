//package com.raksa.app.services.impls;
//
//import com.raksa.app.dto.UserResponseDto;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.nio.charset.StandardCharsets;
//import java.time.Duration;
//import java.time.Instant;
//import java.util.Date;
//import java.security.Key;
//
//@Service
//public class JwtService {
//
//    private final Key key;
//    private final int ttlDays;
//
//    public JwtService(
//            @Value("${spring.security.oauth2.resourceserver.jwt.secret}") String secret,
//            @Value("${spring.security.oauth2.resourceserver.jwt.ttl-days}") int ttlDays) {
//        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
//        this.ttlDays = ttlDays;
//    }
//
//    public String generateToken(UserResponseDto userResponseDto) {
//        Instant now = Instant.now();
//        return Jwts.builder()
//                .claim("email", userResponseDto.getEmail())
//                .setSubject(userResponseDto.getName())
//                .claim("id", userResponseDto.getId())
//                .claim("provider_id", userResponseDto.getProviderId())
//                .claim("provider", userResponseDto.getProvider())
//                .setIssuedAt(Date.from(now))
//                .setExpiration(Date.from(now.plus(Duration.ofDays(ttlDays))))
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//    }
//
////    public Claims parseToken(String token) {
////        return Jwts.parserBuilder()
////                .setSigningKey(key)
////                .build()
////                .parseClaimsJws(token)
////                .getBody();
////    }
////
////    /** Validates the JWT token and returns the username if valid. */
////    public String validateToken(String token) {
////        return parseToken(token).getSubject();
////    }
////
////    /** Extracts claims from the JWT token. */
////    public Claims extractClaims(String token) {
////        return parseToken(token);
////    }
//}
