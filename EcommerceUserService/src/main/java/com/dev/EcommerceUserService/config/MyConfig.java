package com.dev.EcommerceUserService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

@Configuration
public class MyConfig {
    @Bean
    public BCryptPasswordEncoder byBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain filteringCriteria(HttpSecurity http) throws Exception {
//        http
//                .httpBasic(Customizer.withDefaults())
//                .cors(AbstractHttpConfigurer::disable)
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/auth/**").permitAll()
//                        //.anyRequest().authenticated()
//                        .anyRequest().permitAll()
//                );
//        return http.build();
//    }

    @Bean
    public PublicKey loadPublicKey() throws Exception {

        KeyStore keyStore = KeyStore.getInstance("JKS");

        try (InputStream is =
                     getClass().getClassLoader().getResourceAsStream("userService.jks")) {

            keyStore.load(is, "userService".toCharArray());
        }

        Certificate certificate = keyStore.getCertificate("userService");

        return certificate.getPublicKey();
    }

    @Bean
    public PrivateKey loadPrivateKey() throws Exception {

        KeyStore keyStore = KeyStore.getInstance("JKS");

        try (InputStream is =
                     getClass().getClassLoader().getResourceAsStream("userService.jks")) {

            keyStore.load(is, "userService".toCharArray());
        }

        Key key = keyStore.getKey("userService", "userService".toCharArray());

        if (key instanceof PrivateKey privateKey) {
            return privateKey;
        }

        throw new RuntimeException("Private key not found");
    }
}