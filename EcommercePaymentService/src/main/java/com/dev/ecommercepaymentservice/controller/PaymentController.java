package com.dev.ecommercepaymentservice.controller;

import com.dev.ecommercepaymentservice.service.PaymentService;
import com.razorpay.RazorpayException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/{orderId}")
    public String createPaymentLink(@PathVariable("orderId") String orderId) throws RazorpayException {
        return paymentService.createPaymentLink(orderId);
    }

    @PostMapping("/status/{orderId}")
    public String getStatus(String id) {
        return null;
    }

    @PostMapping("/webhook")
    public String handleWebhookEvent(String id) {
        return null;
    }
}
