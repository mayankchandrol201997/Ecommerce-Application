package com.dev.ecommerceorderservice.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class OrderServiceException extends RuntimeException {
    private HttpStatus status;
    public OrderServiceException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
