package com.dev.EcommerceUserService.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserServiceUtil {
    public static boolean isNull(Object object)
    {
        if (object==null)
            return true;
        return false;
    }

    public static UUID toUUID(String id)
    {
        return UUID.fromString(id);
    }

    public static ResponseEntity<Map<String,Object>> buildResponseEntity(Object response, HttpStatus httpStatus)
    {
        HashMap<String,Object> responseMap = new HashMap<>();
        responseMap.put("response",response);
        responseMap.put("message","Success");
        return new ResponseEntity<>(responseMap,httpStatus);
    }

    public static <T> ResponseEntity<Map<String, Object>> buildResponseEntity(Map<String,Object> response,HttpStatus httpStatus)
    {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("response", response.get("response"));
        responseMap.put("message", "Success");
        return new ResponseEntity<>(responseMap, (HttpHeaders) response.get("headers"), httpStatus);
    }
}
