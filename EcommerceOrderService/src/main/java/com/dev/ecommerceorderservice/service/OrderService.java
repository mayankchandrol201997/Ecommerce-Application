package com.dev.ecommerceorderservice.service;

import com.dev.ecommerceorderservice.dto.OrderRequestDto;
import com.dev.ecommerceorderservice.dto.OrderResponseDto;
import com.dev.ecommerceorderservice.security.CurrentUser;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface OrderService {
    HashMap<String, Object> createOrder(OrderRequestDto orderRequestDto, CurrentUser currentUser) throws JsonProcessingException;

    List<OrderResponseDto> getAllOrder(UUID userId);
}
