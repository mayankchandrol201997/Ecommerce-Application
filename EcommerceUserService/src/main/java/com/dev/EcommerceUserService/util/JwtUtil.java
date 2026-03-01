package com.dev.EcommerceUserService.util;

import com.dev.EcommerceUserService.exception.UserServiceException;
import com.dev.EcommerceUserService.model.Role;
import com.dev.EcommerceUserService.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import io.jsonwebtoken.security.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${token.expiry}")
    private Long tokenExpiry;
    @Autowired
    private PrivateKey privateKey;
    @Autowired
    private PublicKey publicKey;

    public String generateToken(User user) {

        SignatureAlgorithm algorithm = Jwts.SIG.RS256;
        Instant createdAt = Instant.now();
        Instant expiryAt = createdAt.plus(tokenExpiry, ChronoUnit.DAYS);

        List<String> roles = user.getRoles()
                .stream()
                .map(Role::getRoleName)
                .toList();

        return Jwts.builder()
                .subject(user.getName())
                .issuedAt(Date.from(createdAt))
                .expiration(Date.from(expiryAt))
                .claim("userId", user.getId())
                .claim("email", user.getEmail())
                .claim("roles", roles)
                .signWith(privateKey,algorithm)
                .compact();
    }

    public Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(publicKey).build().
                    parseSignedClaims(token).getPayload();
        } catch (ExpiredJwtException e) {
            throw new UserServiceException("JWT token has expired", HttpStatus.UNAUTHORIZED);
        } catch (JwtException e) {
            throw new UserServiceException("Invalid JWT token", HttpStatus.BAD_REQUEST);
        }
    }


}
