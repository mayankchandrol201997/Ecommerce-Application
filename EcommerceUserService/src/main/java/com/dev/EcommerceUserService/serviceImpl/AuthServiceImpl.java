package com.dev.EcommerceUserService.serviceImpl;

import com.dev.EcommerceUserService.dto.LoginRequestDto;
import com.dev.EcommerceUserService.dto.SignUpRequestDto;
import com.dev.EcommerceUserService.dto.UserResponseDto;
import com.dev.EcommerceUserService.exception.UserServiceException;
import com.dev.EcommerceUserService.model.Role;
import com.dev.EcommerceUserService.model.Token;
import com.dev.EcommerceUserService.model.TokenStatus;
import com.dev.EcommerceUserService.model.User;
import com.dev.EcommerceUserService.repository.RolesRepository;
import com.dev.EcommerceUserService.repository.TokenRepository;
import com.dev.EcommerceUserService.repository.UserRepository;
import com.dev.EcommerceUserService.service.AuthService;
import com.dev.EcommerceUserService.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.dev.EcommerceUserService.mapper.UserMapper.toUser;
import static com.dev.EcommerceUserService.mapper.UserMapper.toUserResponseDto;

@Service
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private RolesRepository rolesRepository;
    private TokenRepository tokenRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository,RolesRepository rolesRepository, TokenRepository tokenRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
        this.rolesRepository = rolesRepository;
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

    public void logout(String jwtToken) {
        Token storedToken = tokenRepository.findByToken(jwtToken)
                .orElseThrow(() -> new UserServiceException("Invalid token",HttpStatus.UNAUTHORIZED));

        if (storedToken.getTokenStatus() == TokenStatus.ENDED) {
            return;
        }

        storedToken.setTokenStatus(TokenStatus.ENDED);
        tokenRepository.save(storedToken);
    }

    public UserResponseDto signUp(SignUpRequestDto request) {
        String email = request.getEmail();
        if(userRepository.findByEmail(email).isPresent()){
            throw new UserServiceException(
                    "user already exists with emailId "+email, HttpStatus.CONFLICT);
        }
        User user = toUser(request);
        Role role = rolesRepository.findByRoleName("USER").orElseThrow(
                ()-> new UserServiceException("Invalid Role", HttpStatus.BAD_REQUEST));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles((roles));
        user = userRepository.save(user);
        return toUserResponseDto(user);
    }

    public TokenStatus validate(String jwtToken) {
        Token token = tokenRepository.findByToken(jwtToken).orElseThrow(() -> new UserServiceException(
                "Token is invalid",
                HttpStatus.BAD_REQUEST
        ));        return token.getTokenStatus();
    }
}