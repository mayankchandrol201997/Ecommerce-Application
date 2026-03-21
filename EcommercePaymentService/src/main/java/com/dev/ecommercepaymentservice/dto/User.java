package com.dev.ecommercepaymentservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User {
    private String userName; // ACTIVE / INVALID
    private String userId;
    private String mobileNumber;
    private String email;
    private Double amount;
}