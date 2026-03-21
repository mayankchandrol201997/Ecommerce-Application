package com.dev.ecommerceorderservice.dto;

import com.dev.ecommerceorderservice.model.OrderItem;
import com.dev.ecommerceorderservice.model.OrderStatus;
import com.dev.ecommerceorderservice.model.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrderResponseDto {
    private Long id;
    private UUID userId;
    private List<OrderItem> orderItems;
    private Instant createdAt;
    private Instant updatedAt;
    private double totalAmount;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private String paymentId;
}
