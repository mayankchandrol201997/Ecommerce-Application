package com.dev.EcommerceUserService.serviceImpl;

import com.dev.EcommerceUserService.dto.LoginRequestDto;
import com.dev.EcommerceUserService.dto.SignUpRequestDto;
import com.dev.EcommerceUserService.dto.UserResponseDto;
import com.dev.EcommerceUserService.exception.UserServiceException;
import com.dev.EcommerceUserService.model.Token;
import com.dev.EcommerceUserService.model.TokenStatus;
import com.dev.EcommerceUserService.model.User;
import com.dev.EcommerceUserService.repository.TokenRepository;
import com.dev.EcommerceUserService.repository.UserRepository;
import com.dev.EcommerceUserService.service.AuthService;
import com.dev.EcommerceUserService.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.dev.EcommerceUserService.mapper.UserMapper.toUser;
import static com.dev.EcommerceUserService.mapper.UserMapper.toUserResponseDto;

@Service
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, TokenRepository tokenRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public Map<String, Object> login(LoginRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new UserServiceException(
                        String.format("User with email %s not found", requestDto.getEmail()),
                        HttpStatus.NOT_FOUND
                ));

        if (!bCryptPasswordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new UserServiceException("Invalid Credentials",HttpStatus.BAD_REQUEST);
        }

        tokenRepository.findActiveTokenByUserEmail(user.getEmail())
                .ifPresent(token ->
                {
                    token.setTokenStatus(TokenStatus.ENDED);
                    tokenRepository.save(token);
                });

        String token = jwtUtil.generateToken(user);
        Token session = new Token();
        session.setUser(user);
        session.setToken(token);
        session.setTokenStatus(TokenStatus.ACTIVE);
        tokenRepository.save(session);

        HashMap<String,Object> response = new HashMap<>();
        response.put("response",toUserResponseDto(user));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, token);
        response.put("headers",headers);
        return response;
    }

    public void logout(String jwtToken, UUID userId) {
        Token token = tokenRepository.findByTokenAndUser_Id(jwtToken, userId).orElseThrow(
                ()-> new UserServiceException(
                        "Token is invalid",
                        HttpStatus.BAD_REQUEST
                )
        );

        token.setTokenStatus(TokenStatus.ENDED);
        tokenRepository.save(token);
    }

    public UserResponseDto signUp(SignUpRequestDto request) {
        User user = toUser(request);
        user = userRepository.save(user);
        return toUserResponseDto(user);
    }

    public TokenStatus validate(String jwtToken) {
        Claims claims = jwtUtil.parseToken(jwtToken);
        String userId = claims.get("userId", String.class);
        Token token = tokenRepository.findByTokenAndUser_Id(jwtToken, UUID.fromString(userId))
                .orElseThrow(() -> new UserServiceException(
                        "Token is invalid",
                        HttpStatus.BAD_REQUEST
                ));
        return token.getTokenStatus();
    }
}