package com.dev.EcommerceUserService.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
public class Session extends BaseModel{
    private String token;
    private Instant expiringAt;
    @Enumerated(EnumType.STRING)
    private SessionStatus sessionStatus;
    @ManyToOne
    private User user;
}
