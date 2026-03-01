package com.dev.EcommerceUserService.security;

import com.dev.EcommerceUserService.security.service.CustomUserDetailServiceImpl;
import com.dev.EcommerceUserService.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
 
    private final JwtUtil jwtUtil;
    private final CustomUserDetailServiceImpl userDetailsService;
 
    public JwtAuthenticationFilter(JwtUtil jwtUtil,
                                   CustomUserDetailServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }
 
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("doFilterInternal");
        String header = request.getHeader("Authorization");
 
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
 
        String token = header.substring(7);
 
        try {
            Claims claims = jwtUtil.parseToken(token);
            String email = claims.get("email", String.class);

            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(email);
 
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
 
            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);
 
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
 
        filterChain.doFilter(request, response);
    }
}