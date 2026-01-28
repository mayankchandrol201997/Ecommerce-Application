package com.dev.EcommerceUserService.controller;

import com.dev.EcommerceUserService.dto.LoginRequestDto;
import com.dev.EcommerceUserService.dto.SignUpRequestDto;
import com.dev.EcommerceUserService.dto.UserResponseDto;
import com.dev.EcommerceUserService.exception.UserServiceException;
import com.dev.EcommerceUserService.model.SessionStatus;
import com.dev.EcommerceUserService.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody LoginRequestDto request) {
        return authService.login(request);
    }

    @PostMapping("/logout/{id}")
    public ResponseEntity<String> logout(@PathVariable("id") UUID userId, @RequestHeader("token") String token) {
        authService.logout(token, userId);
        return ResponseEntity.ok("Logged out successfully");
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody SignUpRequestDto request) {
        UserResponseDto userResponseDto = authService.signUp(request);
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validateToken(@RequestHeader("Authorization") String token) {
        SessionStatus sessionStatus = authService.validate(extractToken(token));
        return new ResponseEntity<>(sessionStatus, HttpStatus.OK);
    }

    private String extractToken(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new UserServiceException(
                    "Invalid Authorization header",
                    HttpStatus.BAD_REQUEST
            );
        }
        System.out.println(header.substring(7));
        return header.substring(7);
    }
}