package com.dev.ecommerceorderservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CartItemResponseDto {
    private Long id;
    private UUID productId;
    private long quantity;
}
