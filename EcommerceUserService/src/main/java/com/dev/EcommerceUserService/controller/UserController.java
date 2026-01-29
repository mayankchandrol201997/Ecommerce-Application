package com.dev.EcommerceUserService.controller;

import com.dev.EcommerceUserService.dto.UserResponseDto;
import com.dev.EcommerceUserService.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.dev.EcommerceUserService.util.UserServiceUtil.buildResponseEntity;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserDetails(@PathVariable("id") UUID userId) {
        UserResponseDto userResponseDto = userService.getUserDetails(userId);
        return buildResponseEntity(userResponseDto, HttpStatus.OK);
    }

    @PostMapping("/{id}/role")
    public ResponseEntity<Map<String, Object>> setUserRoles(@PathVariable("id") UUID userId, @RequestBody List<UUID> roleIds) {
        UserResponseDto userResponseDto = userService.setUserRoles(userId, roleIds);
        return buildResponseEntity(userResponseDto, HttpStatus.OK);
    }
}