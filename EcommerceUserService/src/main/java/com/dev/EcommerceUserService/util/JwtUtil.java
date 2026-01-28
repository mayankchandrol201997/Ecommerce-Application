package com.dev.EcommerceUserService.util;

import com.dev.EcommerceUserService.exception.UserServiceException;
import com.dev.EcommerceUserService.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.xml.crypto.Data;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${secret.key}")
    private String secretKey;

    public String generateToken(User user) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        Instant createdAt = Instant.now();
        Instant expiryAt = createdAt.plus(3, ChronoUnit.DAYS);

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("emailId", user.getEmail());
        claims.put("roles", user.getRoles());

        return Jwts.builder()
                .setIssuedAt(Date.from(createdAt))
                .setExpiration(Date.from(expiryAt))
                .claims(claims)
                .signWith(key).compact();
    }

    public Claims parseToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        try {
            return Jwts.parser()
                    .verifyWith(key).build().
                    parseSignedClaims(token).getPayload();
        } catch (ExpiredJwtException e) {
            throw new UserServiceException("JWT token has expired", HttpStatus.UNAUTHORIZED);
        } catch (JwtException e) {
            throw new UserServiceException("Invalid JWT token", HttpStatus.BAD_REQUEST);
        }
    }
}
