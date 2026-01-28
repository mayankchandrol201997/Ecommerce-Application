package com.dev.EcommerceUserService.dto;

import com.dev.EcommerceUserService.model.Role;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class UserResponseDto {
    private UUID id;
    private String name;
    private String email;
    private Set<RoleResponseDto> roles;
}
