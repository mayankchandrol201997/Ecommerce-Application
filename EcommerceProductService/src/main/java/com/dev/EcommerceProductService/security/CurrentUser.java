package com.dev.EcommerceProductService.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

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

    public UUID getUserId() {
        return userId;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}