package com.dev.ecommerceorderservice.mapper;

import com.dev.ecommerceorderservice.dto.CartItemResponseDto;
import com.dev.ecommerceorderservice.dto.CartResponseDto;
import com.dev.ecommerceorderservice.model.Cart;
import com.dev.ecommerceorderservice.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartMapper {
    public static CartResponseDto toCartResponseDto(Cart cart)
    {
        CartResponseDto cartResponseDto = new CartResponseDto();
        cartResponseDto.setId(cart.getId());
        cartResponseDto.setUserId(cart.getUserId());

        List<CartItemResponseDto> cartItemResponseDtos = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            cartItemResponseDtos.add(toCartItemResponseDto(cartItem));
        }
        cartResponseDto.setCartItems(cartItemResponseDtos);
        return cartResponseDto;
    }

    public  static CartItemResponseDto toCartItemResponseDto(CartItem cartItem)
    {
        CartItemResponseDto cartItemResponseDto = new CartItemResponseDto();
        cartItemResponseDto.setId(cartItem.getId());
        cartItemResponseDto.setQuantity(cartItem.getQuantity());
        cartItemResponseDto.setProductId(cartItem.getProductId());
        return cartItemResponseDto;
    }
}
