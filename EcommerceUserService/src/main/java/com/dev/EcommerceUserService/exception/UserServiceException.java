package com.dev.EcommerceUserService.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class UserServiceException extends RuntimeException {
    private HttpStatus httpStatus;
    public UserServiceException(String message,HttpStatus httpstatus) {
        super(message);
        httpStatus = httpstatus;
    }
}
