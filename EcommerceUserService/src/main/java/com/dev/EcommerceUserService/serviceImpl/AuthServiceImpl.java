package com.dev.EcommerceUserService.serviceImpl;

import com.dev.EcommerceUserService.dto.LoginRequestDto;
import com.dev.EcommerceUserService.dto.SignUpRequestDto;
import com.dev.EcommerceUserService.dto.UserResponseDto;
import com.dev.EcommerceUserService.exception.UserServiceException;
import com.dev.EcommerceUserService.model.Session;
import com.dev.EcommerceUserService.model.SessionStatus;
import com.dev.EcommerceUserService.model.User;
import com.dev.EcommerceUserService.repository.SessionRepository;
import com.dev.EcommerceUserService.repository.UserRepository;
import com.dev.EcommerceUserService.service.AuthService;
import com.dev.EcommerceUserService.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import java.util.HashMap;
import java.util.UUID;

import static com.dev.EcommerceUserService.mapper.UserMapper.toUser;
import static com.dev.EcommerceUserService.mapper.UserMapper.toUserResponseDto;

@Service
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, SessionRepository sessionRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<UserResponseDto> login(LoginRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new UserServiceException(
                        String.format("User with email %s not found", requestDto.getEmail()),
                        HttpStatus.NOT_FOUND
                ));

        if (!bCryptPasswordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new UserServiceException("Invalid Credentials",HttpStatus.BAD_REQUEST);
        }

        sessionRepository.findActiveSessionByUserEmail(user.getEmail())
                .ifPresent(session ->
                {
                    session.setSessionStatus(SessionStatus.ENDED);
                    sessionRepository.save(session);
                });

        String token = jwtUtil.generateToken(user);
        Session session = new Session();
        session.setUser(user);
        session.setToken(token);
        session.setSessionStatus(SessionStatus.ACTIVE);
        sessionRepository.save(session);

        UserResponseDto userResponseDto = toUserResponseDto(user);
        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token:"+ token);
        return new ResponseEntity<>(userResponseDto, headers, HttpStatus.OK);
    }

    public void logout(String token, UUID userId) {
        Session session = sessionRepository.findByTokenAndUser_Id(token, userId).orElseThrow(
                ()-> new UserServiceException(
                        "Token is invalid",
                        HttpStatus.BAD_REQUEST
                )
        );

        session.setSessionStatus(SessionStatus.ENDED);
        sessionRepository.save(session);
    }

    public UserResponseDto signUp(SignUpRequestDto request) {
        User user = toUser(request);
        user = userRepository.save(user);
        return toUserResponseDto(user);
    }

    public SessionStatus validate(String token) {
        Claims claims = jwtUtil.parseToken(token);
        String userId = claims.get("userId", String.class);
        Session session = sessionRepository.findByTokenAndUser_Id(token, UUID.fromString(userId))
                .orElseThrow(() -> new UserServiceException(
                        "Token is invalid",
                        HttpStatus.BAD_REQUEST
                ));
        return session.getSessionStatus();
    }
}