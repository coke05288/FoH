package com.hzipyb.userservice.domain.user.service;

import com.hzipyb.userservice.domain.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@RequiredArgsConstructor
@Service
@Slf4j
public class JWTService {
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secretkey;

    public String generateToken(User existingUser, String requestPassword){
        if(!passwordEncoder.matches(requestPassword, existingUser.getPasswordHash())){
            throw new IllegalArgumentException("Invalid credentials");
        }

        long currentTimeMillis = System.currentTimeMillis();

        return Jwts.builder()
                .subject(existingUser.getEmail())
                .issuedAt(new Date(currentTimeMillis))
                .expiration(new Date(currentTimeMillis + 3600000 * 3))
                .signWith(Keys.hmacShaKeyFor(secretkey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            parseJwtClaims(token);
            return true;
        } catch (Exception e) {
            log.error("validateToken error : ", e);
            return false;
        }
    }

    public String refreshToken(String token) {
        Claims claims = parseJwtClaims(token);

        long currentTimeMillis = System.currentTimeMillis();

        return Jwts.builder()
                .subject(claims.getSubject())
                .issuedAt(new Date(currentTimeMillis))
                .expiration(new Date(currentTimeMillis + 3600000 * 3)) // Token expires in 1 hour
                .signWith(Keys.hmacShaKeyFor(secretkey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public Claims parseJwtClaims(String token){
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretkey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}