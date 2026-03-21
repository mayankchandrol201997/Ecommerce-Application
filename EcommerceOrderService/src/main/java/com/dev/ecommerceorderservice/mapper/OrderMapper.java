package com.dev.ecommerceorderservice.mapper;

import com.dev.ecommerceorderservice.dto.OrderResponseDto;
import com.dev.ecommerceorderservice.model.Order;

public class OrderMapper {

    public static OrderResponseDto toOrderResponseDto(Order order){
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(order.getId());
        orderResponseDto.setOrderStatus(order.getOrderStatus());
        orderResponseDto.setCreatedAt(order.getCreatedAt());
        orderResponseDto.setUpdatedAt(order.getUpdatedAt());
        orderResponseDto.setPaymentStatus(order.getPaymentStatus());
        orderResponseDto.setUserId(order.getUserId());
        orderResponseDto.setPaymentId(order.getPaymentId());
        orderResponseDto.setTotalAmount(order.getTotalAmount());
        orderResponseDto.setOrderItems(order.getOrderItems());
        return orderResponseDto;
    }
}
