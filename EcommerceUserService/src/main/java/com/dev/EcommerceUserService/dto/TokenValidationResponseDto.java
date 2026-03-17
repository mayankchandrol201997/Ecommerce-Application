package com.dev.EcommerceUserService.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class TokenValidationResponseDto {
    private String status;
    private UUID userId;
    private String email;
    private List<String> roles;
}