package com.dev.ecommerceorderservice.service;

import com.dev.ecommerceorderservice.dto.AddToCartRequestDto;
import com.dev.ecommerceorderservice.dto.CartResponseDto;

import java.util.UUID;

public interface CartService {
    CartResponseDto addToCart(UUID userId, AddToCartRequestDto addToCartRequestDto);

    CartResponseDto getCartByUserId(UUID userId);

    CartResponseDto updateItemQuantity(UUID userId, UUID productId, long quantity);

    void removeItem(UUID userId, UUID productId);
}
