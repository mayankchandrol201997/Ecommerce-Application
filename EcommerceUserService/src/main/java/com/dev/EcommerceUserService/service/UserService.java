package com.dev.EcommerceUserService.service;

import com.dev.EcommerceUserService.dto.UserResponseDto;
import com.dev.EcommerceUserService.model.Role;

import java.util.List;
import java.util.UUID;

public interface UserService {
    public UserResponseDto getUserDetails(UUID userid);
    public UserResponseDto setUserRoles(UUID userid, List<UUID> roleIds);
}
