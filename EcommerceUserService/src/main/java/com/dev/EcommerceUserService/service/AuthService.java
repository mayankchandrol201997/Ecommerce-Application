package com.dev.EcommerceUserService.service;

import com.dev.EcommerceUserService.dto.LoginRequestDto;
import com.dev.EcommerceUserService.dto.SignUpRequestDto;
import com.dev.EcommerceUserService.dto.UserResponseDto;
import com.dev.EcommerceUserService.model.TokenStatus;

import java.util.Map;
import java.util.UUID;

public interface AuthService {
    Map<String, Object> login(LoginRequestDto requestDto);
    void logout(String jwtToken, UUID userId);
    UserResponseDto signUp(SignUpRequestDto request);
    TokenStatus validate(String jwtToken);
}
