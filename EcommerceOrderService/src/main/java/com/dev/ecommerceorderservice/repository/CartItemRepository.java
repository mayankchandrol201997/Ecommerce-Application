package com.dev.ecommerceorderservice.repository;

import com.dev.ecommerceorderservice.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteCartItemByProductId(UUID productId);
}
