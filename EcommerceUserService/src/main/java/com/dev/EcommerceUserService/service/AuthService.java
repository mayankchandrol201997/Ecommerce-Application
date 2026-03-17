package com.dev.EcommerceUserService.service;

import com.dev.EcommerceUserService.dto.LoginRequestDto;
import com.dev.EcommerceUserService.dto.SignUpRequestDto;
import com.dev.EcommerceUserService.dto.TokenValidationResponseDto;
import com.dev.EcommerceUserService.dto.UserResponseDto;

import java.util.Map;

public interface AuthService {
    Map<String, Object> login(LoginRequestDto requestDto);
    void logout(String jwtToken);
    UserResponseDto signUp(SignUpRequestDto request);
    TokenValidationResponseDto validate(String jwtToken);
}
