package com.dev.ecommerceorderservice.service;

import com.dev.ecommerceorderservice.dto.AddToCartRequestDto;
import com.dev.ecommerceorderservice.dto.CartResponseDto;
import com.dev.ecommerceorderservice.exception.OrderServiceException;
import com.dev.ecommerceorderservice.mapper.CartMapper;
import com.dev.ecommerceorderservice.model.Cart;
import com.dev.ecommerceorderservice.model.CartItem;
import com.dev.ecommerceorderservice.repository.CartItemRepository;
import com.dev.ecommerceorderservice.repository.CartRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.dev.ecommerceorderservice.mapper.CartMapper.toCartResponseDto;

@Service
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public CartResponseDto addToCart(UUID userId, AddToCartRequestDto addToCartRequestDto) {
        UUID productId = addToCartRequestDto.getProductId();
        long quantity = addToCartRequestDto.getQuantity();
        AtomicBoolean isExistingCartItem = new AtomicBoolean(true);

        CartItem cartItem =  new CartItem();
        cartItem.setQuantity(quantity);
        cartItem.setProductId(productId);

        Cart cart = cartRepository.findCartByUserId(userId).orElseGet(
                ()-> {
                    isExistingCartItem.set(false);
                    Cart newCart = new Cart();
                    newCart.setUserId(userId);
                    newCart.setCartItems(List.of(cartItem));
                    return newCart;
                }
        );

        if(isExistingCartItem.get()) {
            cart.getCartItems().stream().filter(
                    existingCartItem -> existingCartItem.getProductId().equals(productId)
            ).findFirst().ifPresent(existingCartItem -> {
                existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            });
        }

        cart = cartRepository.save(cart);
        return toCartResponseDto(cart);
    }

    @Override
    public CartResponseDto getCartByUserId(UUID userId) {
        Cart cart = cartRepository.findCartByUserId(userId).orElseThrow(
                ()-> new OrderServiceException("Cart is Empty", HttpStatus.NOT_FOUND)
        );
        return toCartResponseDto(cart);
    }

    @Override
    public CartResponseDto updateItemQuantity(UUID userId, UUID productId, long quantity) {
        Cart cart = cartRepository.findCartByUserId(userId).orElseThrow(
                ()-> new OrderServiceException("product not present in Cart for productId "+productId, HttpStatus.NOT_FOUND)
        );

        cart.getCartItems().stream().filter(
                    existingCartItem -> existingCartItem.getProductId().equals(productId)
            ).findFirst().ifPresent(existingCartItem -> {
                existingCartItem.setQuantity(quantity);
            });

        cart = cartRepository.save(cart);
        return toCartResponseDto(cart);
    }

    @Override
    public void removeItem(UUID userId, UUID productId) {
        cartItemRepository.deleteCartItemByProductId(productId);
    }
}
