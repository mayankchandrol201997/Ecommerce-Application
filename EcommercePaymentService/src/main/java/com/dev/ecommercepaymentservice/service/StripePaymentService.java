package com.dev.ecommercepaymentservice.service;

import com.dev.ecommercepaymentservice.dto.User;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentService implements PaymentService{
    @Override
    public String createPaymentLink(String orderId, User user) {
        return "";
    }

    @Override
    public String getStatus(String id) {
        return "";
    }
}
