package com.dev.EcommerceUserService.serviceImpl;

import com.dev.EcommerceUserService.dto.UserResponseDto;
import com.dev.EcommerceUserService.exception.UserServiceException;
import com.dev.EcommerceUserService.model.Role;
import com.dev.EcommerceUserService.model.User;
import com.dev.EcommerceUserService.repository.RolesRepository;
import com.dev.EcommerceUserService.repository.UserRepository;
import com.dev.EcommerceUserService.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.dev.EcommerceUserService.mapper.UserMapper.toUserResponseDto;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RolesRepository rolesRepository;

    public UserServiceImpl(UserRepository userRepository, RolesRepository rolesRepository) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
    }

    @Override
    public UserResponseDto getUserDetails(UUID userid) {
        User user = userRepository.findById(userid).orElseThrow(
                () -> new UserServiceException("User not found with id "+userid, HttpStatus.NOT_FOUND)
        );
        return toUserResponseDto(user);
    }

    @Override
    public UserResponseDto setUserRoles(UUID userid, List<UUID> roleIds) {
        if(roleIds.isEmpty())
            throw new UserServiceException("roleId cant be empty.", HttpStatus.BAD_REQUEST);

        User user = userRepository.findById(userid).orElseThrow(
                () -> new UserServiceException("User not found with id "+userid, HttpStatus.NOT_FOUND));

        List<Role> roles =  rolesRepository.findAllById_In(roleIds);
        if(roles.isEmpty())
            throw new UserServiceException("Role not found for the given roleId", HttpStatus.NOT_FOUND);

        user.getRoles().addAll(new HashSet<>(roles));
        user = userRepository.save(user);
        return toUserResponseDto(user);
    }
}
