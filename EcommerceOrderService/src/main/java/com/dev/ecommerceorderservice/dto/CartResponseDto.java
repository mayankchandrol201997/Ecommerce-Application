package com.dev.ecommerceorderservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CartResponseDto {
    private Long id;
    private UUID userId;
    private List<CartItemResponseDto> cartItems;
}
