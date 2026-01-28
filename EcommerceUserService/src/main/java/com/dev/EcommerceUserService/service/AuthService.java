package com.dev.EcommerceUserService.service;

import com.dev.EcommerceUserService.dto.LoginRequestDto;
import com.dev.EcommerceUserService.dto.SignUpRequestDto;
import com.dev.EcommerceUserService.dto.UserResponseDto;
import com.dev.EcommerceUserService.model.SessionStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface AuthService {
    ResponseEntity<UserResponseDto> login(LoginRequestDto requestDto);
    void logout(String token, UUID userId);
    UserResponseDto signUp(SignUpRequestDto request);
    SessionStatus validate(String token);
}
