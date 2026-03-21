package com.dev.ecommerceorderservice.service;

import com.dev.ecommerceorderservice.client.PaymentServiceClient;
import com.dev.ecommerceorderservice.dto.OrderItemRequestDto;
import com.dev.ecommerceorderservice.dto.OrderRequestDto;
import com.dev.ecommerceorderservice.dto.OrderResponseDto;
import com.dev.ecommerceorderservice.dto.User;
import com.dev.ecommerceorderservice.mapper.OrderMapper;
import com.dev.ecommerceorderservice.model.Order;
import com.dev.ecommerceorderservice.model.OrderItem;
import com.dev.ecommerceorderservice.model.PaymentStatus;
import com.dev.ecommerceorderservice.repository.OrderRepository;
import com.dev.ecommerceorderservice.security.CurrentUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.dev.ecommerceorderservice.mapper.OrderMapper.toOrderResponseDto;

@Service
public class OrderServiceImpl implements OrderService{
    private OrderRepository orderRepository;
    private PaymentServiceClient paymentServiceClient;

    public OrderServiceImpl(OrderRepository orderRepository, PaymentServiceClient paymentServiceClient) {
        this.orderRepository = orderRepository;
        this.paymentServiceClient = paymentServiceClient;
    }

    @Override
    @Transactional
    public HashMap<String, Object> createOrder(OrderRequestDto orderRequestDto, CurrentUser currentUser) throws JsonProcessingException {
        List<OrderItem>  orderItems = new ArrayList<>();
        for (OrderItemRequestDto orderItemRequestDto : orderRequestDto.getOrderItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setPrice(orderItemRequestDto.getPrice());
            orderItem.setQuantity(orderItemRequestDto.getQuantity());
            orderItems.add(orderItem);
        }
        Order order = new Order();
        order.setOrderItems(orderItems);
        order.setTotalAmount(orderRequestDto.getTotalAmount());
        System.out.println(currentUser.getUserId());
        order.setUserId(currentUser.getUserId());
        order.setPaymentStatus(PaymentStatus.PAYMENT_PENDING);
        order = orderRepository.save(order);

        User user = new User();
        user.setUserId(String.valueOf(currentUser.getUserId()));
        user.setAmount(orderRequestDto.getTotalAmount());
        user.setEmail(currentUser.getEmail());
        String paymentLink = paymentServiceClient.createPaymentLink(order.getId(),user);

        HashMap<String,Object> response = new HashMap<>();
        response.put("response", toOrderResponseDto(order));
        response.put("paymentLink",paymentLink);
        return response;
    }

    @Override
    public List<OrderResponseDto> getAllOrder(UUID userId) {
        System.out.println(userId);
        return orderRepository.findAllByUserId(userId).stream().map(OrderMapper::toOrderResponseDto).toList();
    }
}
