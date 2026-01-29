package com.dev.EcommerceUserService.service;

import com.dev.EcommerceUserService.dto.RoleResponseDto;
import com.dev.EcommerceUserService.model.Role;

import java.util.List;

public interface RoleService {
    public RoleResponseDto createRole(String roleName);

    List<RoleResponseDto> getAllRole();
}

