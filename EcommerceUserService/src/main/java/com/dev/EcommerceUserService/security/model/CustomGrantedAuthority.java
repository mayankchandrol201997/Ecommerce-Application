package com.dev.EcommerceUserService.security.model;

import com.dev.EcommerceUserService.model.Role;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.GrantedAuthority;

@JsonDeserialize
public class CustomGrantedAuthority implements GrantedAuthority {
    private String authority;

    public CustomGrantedAuthority() {

    }

    CustomGrantedAuthority(Role role)
    {
        this.authority=role.getRoleName();
    }
    @Override
    public String getAuthority() {
        return authority;
    }
}
