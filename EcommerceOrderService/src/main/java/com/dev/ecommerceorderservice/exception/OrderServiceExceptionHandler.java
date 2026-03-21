package com.dev.ecommerceorderservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.stream.Collectors;

@ControllerAdvice
public class OrderServiceExceptionHandler {

    @ExceptionHandler(OrderServiceException.class)
    public ResponseEntity<HashMap<String, Object>> handleProductServiceException(OrderServiceException orderServiceException) {
        return new ResponseEntity<>(buildResponseEntity(orderServiceException.getMessage()), orderServiceException.getHttpStatus());
    }

    private HashMap<String, Object> buildResponseEntity(String message) {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("response", message);
        responseMap.put("message", "Failure");
        return responseMap;
    }
}
