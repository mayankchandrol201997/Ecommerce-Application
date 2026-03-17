package com.dev.ecommercepaymentservice.service;

import org.springframework.stereotype.Service;

@Service
public class StripePaymentService implements PaymentService{
    @Override
    public String createPaymentLink(String orderId) {
        return "";
    }

    @Override
    public String getStatus(String id) {
        return "";
    }
}
