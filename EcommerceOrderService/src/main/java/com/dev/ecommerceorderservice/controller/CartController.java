package com.dev.ecommerceorderservice.controller;

import com.dev.ecommerceorderservice.dto.AddToCartRequestDto;
import com.dev.ecommerceorderservice.dto.CartResponseDto;
import com.dev.ecommerceorderservice.security.CurrentUser;
import com.dev.ecommerceorderservice.security.SecurityUtils;
import com.dev.ecommerceorderservice.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {

    private CartService cartService;
    
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponseDto> addItem(@RequestBody AddToCartRequestDto request) {
        CurrentUser currentUser = SecurityUtils.getCurrentUser();
        CartResponseDto cartResponseDto = cartService.addToCart(currentUser.getUserId(),request);
        return ResponseEntity.ok(cartResponseDto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartResponseDto> getCart(@PathVariable UUID userId) {
        CartResponseDto cartResponseDto = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cartResponseDto);
    }

    @PutMapping("/items/{productId}/{quantity}/{userId}")
    public ResponseEntity<CartResponseDto> updateItem(@PathVariable UUID productId,
                                                      @PathVariable UUID userId,
                                                      @PathVariable long quantity) {
        CartResponseDto cartResponseDto = cartService.updateItemQuantity(userId,productId, quantity);
        return ResponseEntity.ok(cartResponseDto);
    }

    @DeleteMapping("/items/{productId}/{userId}")
    public ResponseEntity<?> removeItem(@PathVariable UUID productId, @PathVariable UUID userId) {
        cartService.removeItem(userId,productId);
        return ResponseEntity.ok("Removed");
    }
}