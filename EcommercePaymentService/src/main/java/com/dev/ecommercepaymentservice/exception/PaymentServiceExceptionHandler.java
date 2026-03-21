package com.dev.ecommercepaymentservice.exception;

import com.razorpay.RazorpayException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.stream.Collectors;

@ControllerAdvice
public class PaymentServiceExceptionHandler {

    @ExceptionHandler(PaymentServiceException.class)
    public ResponseEntity<HashMap<String, Object>> handlePaymentServiceException(PaymentServiceException paymentServiceException) {
        return new ResponseEntity<>(buildResponseEntity(paymentServiceException.getMessage()), paymentServiceException.getHttpStatus());
    }

    @ExceptionHandler(RazorpayException.class)
    public ResponseEntity<HashMap<String, Object>> handleRazorpayException(RazorpayException razorpayException) {
        return new ResponseEntity<>(buildResponseEntity(razorpayException.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HashMap<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        String message = methodArgumentNotValidException.getBindingResult().getFieldErrors().stream()
                .map(e->e.getField()+" : "+e.getDefaultMessage())
                .collect(Collectors.joining(","));
        return new ResponseEntity<>(buildResponseEntity(message), methodArgumentNotValidException.getStatusCode());
    }

    private HashMap<String, Object> buildResponseEntity(String message) {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("response", message);
        responseMap.put("message", "Failure");
        return responseMap;
    }
}
