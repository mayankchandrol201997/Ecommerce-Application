package com.dev.EcommerceProductService.security.filter;

import com.dev.EcommerceProductService.client.UserServiceClient;
import com.dev.EcommerceProductService.dto.TokenValidationResponseDto;
import com.dev.EcommerceProductService.security.CurrentUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class CustomAuthFilter extends OncePerRequestFilter {
    private UserServiceClient userServiceClient;

    public CustomAuthFilter(UserServiceClient userServiceClient) {
        this.userServiceClient=userServiceClient;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            TokenValidationResponseDto tokenValidationResponseDto = userServiceClient.validateToken(token);

            if (tokenValidationResponseDto != null && "ACTIVE".equals(tokenValidationResponseDto.getStatus())) {
                CurrentUser user = new CurrentUser(
                        UUID.fromString(tokenValidationResponseDto.getUserId()),
                        tokenValidationResponseDto.getEmail(),
                        tokenValidationResponseDto.getRoles().stream()
                                .map(SimpleGrantedAuthority::new)
                                .toList()
                );

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                user.getAuthorities()
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        }
        filterChain.doFilter(request, response);
    }
}