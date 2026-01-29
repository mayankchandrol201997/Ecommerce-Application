package com.dev.EcommerceUserService.controller;

import com.dev.EcommerceUserService.dto.RoleResponseDto;
import com.dev.EcommerceUserService.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.dev.EcommerceUserService.util.UserServiceUtil.buildResponseEntity;

@RestController
@RequestMapping("/role")
public class RoleController {
    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("{name}")
    public ResponseEntity<Map<String, Object>> createRole(@PathVariable("name") String roleName) {
        RoleResponseDto roleResponseDto = roleService.createRole(roleName);
        return buildResponseEntity(roleResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllRole() {
        List<RoleResponseDto> roleResponseDto = roleService.getAllRole();
        return buildResponseEntity(roleResponseDto, HttpStatus.OK);
    }
}