package com.dev.ecommercepaymentservice.controller;

import com.dev.ecommercepaymentservice.PaymentServiceUtil;
import com.dev.ecommercepaymentservice.dto.User;
import com.dev.ecommercepaymentservice.service.PaymentService;
import com.razorpay.RazorpayException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/{orderId}")
    public ResponseEntity<Map<String, Object>> createPaymentLink(@PathVariable String orderId, @RequestBody User user) throws RazorpayException {
        return PaymentServiceUtil.buildResponseEntity(paymentService.createPaymentLink(orderId,user), HttpStatus.OK);
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
