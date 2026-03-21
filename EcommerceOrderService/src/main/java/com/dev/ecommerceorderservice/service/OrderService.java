package com.dev.ecommerceorderservice.service;

import com.dev.ecommerceorderservice.dto.OrderRequestDto;
import com.dev.ecommerceorderservice.security.CurrentUser;

import java.util.HashMap;

public interface OrderService {
    HashMap<String, Object> createOrder(OrderRequestDto orderRequestDto, CurrentUser currentUser);
}
