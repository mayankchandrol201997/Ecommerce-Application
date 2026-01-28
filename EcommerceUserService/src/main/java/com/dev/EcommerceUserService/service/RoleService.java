package com.dev.EcommerceUserService.service;

import com.dev.EcommerceUserService.dto.RoleResponseDto;
import com.dev.EcommerceUserService.model.Role;

public interface RoleService {
    public RoleResponseDto createRole(String roleName);
}

