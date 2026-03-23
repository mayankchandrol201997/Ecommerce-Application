package com.dev.configurationServer.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class securityConfig {
    @Bean
    public SecurityFilterChain security(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())   // 🔥 THIS FIXES YOUR ISSUE
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/encrypt", "/decrypt").authenticated()
                        .anyRequest().authenticated()
                )
                .httpBasic();

        return http.build();
    }
}
