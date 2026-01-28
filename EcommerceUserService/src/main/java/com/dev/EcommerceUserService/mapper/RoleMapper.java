package com.dev.EcommerceUserService.mapper;

import com.dev.EcommerceUserService.dto.RoleResponseDto;
import com.dev.EcommerceUserService.model.Role;

public class RoleMapper {

    public static RoleResponseDto toRoleResponseDto(Role role){
        RoleResponseDto roleResponseDto = new RoleResponseDto();
        roleResponseDto.setId(role.getId());
        roleResponseDto.setRole(role.getRole());
        return roleResponseDto;
    }
}
