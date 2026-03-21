package com.dev.ecommerceorderservice.dto;

import com.dev.ecommerceorderservice.model.OrderItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrderRequestDto {
    private List<OrderItemRequestDto> orderItems;
    private double totalAmount;
}
