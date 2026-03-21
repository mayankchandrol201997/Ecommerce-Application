package com.dev.ecommerceorderservice.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
public class CurrentUser {

    private UUID userId;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;

    public CurrentUser(UUID userId, String email,
                       Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.email = email;
        this.authorities = authorities;
    }
}