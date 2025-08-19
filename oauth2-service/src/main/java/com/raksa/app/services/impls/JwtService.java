//package com.raksa.app.services.impls;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jws;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.nio.charset.StandardCharsets;
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.Map;
//
//@Service
//public class JwtService {
//    @Value("${app.jwt.secret}") private String secret;
//    @Value("${app.jwt.expirySeconds}") private long expiry;
//
//    public String createToken(String subject, Map<String, Object> claims) {
//        LocalDateTime now = LocalDateTime.now();
//        long expiryMillis = 30L * 24 * 60 * 60 * 1000;
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(subject)
//                .setIssuedAt(new java.util.Date())
//                .setExpiration(new Date(System.currentTimeMillis() + expiryMillis))
//                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
//                .compact();
//    }
//
//    public Jws<Claims> parse(String jwt) {
//        return Jwts.parserBuilder()
//                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
//                .build()
//                .parseClaimsJws(jwt);
//    }
//}
