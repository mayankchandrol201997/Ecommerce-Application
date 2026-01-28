package com.dev.EcommerceProductService.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ProductServiceException extends RuntimeException {
    private HttpStatus httpStatus;
    public ProductServiceException(String message,HttpStatus httpstatus) {
        super(message);
        httpStatus = httpstatus;
    }
}
