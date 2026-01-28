package com.dev.EcommerceUserService.serviceImpl;

import com.dev.EcommerceUserService.dto.RoleResponseDto;
import com.dev.EcommerceUserService.model.Role;
import com.dev.EcommerceUserService.repository.RolesRepository;
import com.dev.EcommerceUserService.service.RoleService;
import org.springframework.stereotype.Service;

import static com.dev.EcommerceUserService.mapper.RoleMapper.toRoleResponseDto;

@Service
public class RoleServiceImpl implements RoleService {

    private final RolesRepository rolesRepository;

    public RoleServiceImpl(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public RoleResponseDto createRole(String roleName) {
        Role role = new Role();
        role.setRole(roleName);
        role = rolesRepository.save(role);
        return toRoleResponseDto(role);
    }
}
