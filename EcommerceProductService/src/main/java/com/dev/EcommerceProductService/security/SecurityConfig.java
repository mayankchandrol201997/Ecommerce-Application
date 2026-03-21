package com.dev.EcommerceProductService.security;

import com.dev.EcommerceProductService.security.filter.CustomAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private CustomAuthFilter customAuthFilter;

    public SecurityConfig(CustomAuthFilter customAuthFilter) {
        this.customAuthFilter = customAuthFilter;
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthConverter());
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers(HttpMethod.GET, "/product/{id}", "/product", "/search/**").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers("/product/**", "/category/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
        );
        http.addFilterBefore(customAuthFilter, BearerTokenAuthenticationFilter.class);

        return http.build();
    }
}
