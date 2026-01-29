package com.dev.EcommerceUserService.serviceImpl;

import com.dev.EcommerceUserService.dto.RoleResponseDto;
import com.dev.EcommerceUserService.mapper.RoleMapper;
import com.dev.EcommerceUserService.model.Role;
import com.dev.EcommerceUserService.repository.RolesRepository;
import com.dev.EcommerceUserService.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<RoleResponseDto> getAllRole() {
        List<Role> roles = rolesRepository.findAll();
        return roles.stream().map(RoleMapper::toRoleResponseDto).toList();
    }
}
