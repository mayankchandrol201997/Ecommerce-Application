package com.dev.ecommerceorderservice.controller;

import com.dev.ecommerceorderservice.dto.OrderRequestDto;
import com.dev.ecommerceorderservice.dto.OrderResponseDto;
import com.dev.ecommerceorderservice.model.Order;
import com.dev.ecommerceorderservice.security.CurrentUser;
import com.dev.ecommerceorderservice.security.SecurityUtils;
import com.dev.ecommerceorderservice.service.OrderService;
import com.dev.ecommerceorderservice.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.dev.ecommerceorderservice.util.OrderUtil.buildResponseEntity;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        CurrentUser user = SecurityUtils.getCurrentUser();
        HashMap<String, Object> response = orderService.createOrder(orderRequestDto,user);
        return buildResponseEntity(response, HttpStatus.OK);
    }
}
