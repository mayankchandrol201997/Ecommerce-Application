package com.dev.ecommercepaymentservice.service;

import com.razorpay.RazorpayException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public interface PaymentService {
    public String createPaymentLink(String orderId) throws RazorpayException;
    public String getStatus(String id);
}
