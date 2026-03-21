package com.dev.ecommercepaymentservice.service;

import com.dev.ecommercepaymentservice.dto.User;
import com.razorpay.RazorpayException;

public interface PaymentService {
    public String createPaymentLink(String orderId, User user) throws RazorpayException;
    public String getStatus(String id);
}
