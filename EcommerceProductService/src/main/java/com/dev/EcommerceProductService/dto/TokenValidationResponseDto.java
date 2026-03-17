package com.dev.EcommerceProductService.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TokenValidationResponseDto {
    private String status; // ACTIVE / INVALID
    private String userId;
    private String email;
    private List<String> roles;
}