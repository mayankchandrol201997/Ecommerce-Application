package com.dev.EcommerceProductService.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false,columnDefinition = "BINARY(16)")
    private UUID id;
    @CreationTimestamp
    private Instant createdAt;
    private String createdBy;
    @UpdateTimestamp
    private Instant updatedAt;
    private String updatedBy;
}
