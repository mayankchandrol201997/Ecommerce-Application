package com.dev.ecommercepaymentservice.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class PaymentServiceException extends RuntimeException {
    private HttpStatus httpStatus;
    public PaymentServiceException(String message, HttpStatus status) {
        super(message);
        this.httpStatus = status;
    }
}
