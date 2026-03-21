package com.dev.ecommerceorderservice.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class OrderUtil {

    public static <T> ResponseEntity<Map<String, Object>> buildResponseEntity(Map<String,Object> response, HttpStatus httpStatus)
    {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("response", response.get("response"));
        responseMap.put("message", "Success");

        HttpHeaders headers = new HttpHeaders();
        headers.add("paymentLink", String.valueOf(response.get("paymentLink")));

        return new ResponseEntity<>(responseMap, headers, httpStatus);
    }

    public static ResponseEntity<Map<String,Object>> buildResponseEntity(Object response, HttpStatus httpStatus)
    {
        HashMap<String,Object> responseMap = new HashMap<>();
        responseMap.put("response",response);
        responseMap.put("message","Success");
        return new ResponseEntity<>(responseMap,httpStatus);
    }
}
