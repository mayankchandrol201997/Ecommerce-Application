package com.dev.EcommerceProductService.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.stream.Collectors;

@ControllerAdvice
public class ProductExceptionHandler {

    @ExceptionHandler(ProductServiceException.class)
    public ResponseEntity<HashMap<String, Object>> handleProductServiceException(ProductServiceException productServiceException) {
        return new ResponseEntity<>(buildResponseEntity(productServiceException.getMessage()), productServiceException.getHttpStatus());
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
