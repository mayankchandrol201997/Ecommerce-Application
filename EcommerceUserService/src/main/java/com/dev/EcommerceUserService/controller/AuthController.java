package com.dev.EcommerceUserService.controller;

import com.dev.EcommerceUserService.dto.LoginRequestDto;
import com.dev.EcommerceUserService.dto.SignUpRequestDto;
import com.dev.EcommerceUserService.dto.TokenValidationResponseDto;
import com.dev.EcommerceUserService.dto.UserResponseDto;
import com.dev.EcommerceUserService.exception.UserServiceException;
import com.dev.EcommerceUserService.model.TokenStatus;
import com.dev.EcommerceUserService.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

import static com.dev.EcommerceUserService.util.UserServiceUtil.buildResponseEntity;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequestDto request) {
        return buildResponseEntity(authService.login(request),HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        String Jwt_token = extractToken(token);
        authService.logout(Jwt_token);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signUp(@Valid @RequestBody SignUpRequestDto request) {
        UserResponseDto userResponseDto = authService.signUp(request);
        return buildResponseEntity(userResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestHeader("Authorization") String token) {
        System.out.println("controller----------"+token);
        TokenValidationResponseDto tokenValidationResponseDto = authService.validate(extractToken(token));
        return buildResponseEntity(tokenValidationResponseDto, HttpStatus.OK);
    }

    private String extractToken(String header) {
        return header.substring(7);
    }
}