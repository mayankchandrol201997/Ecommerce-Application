package com.dev.EcommerceUserService.mapper;

import com.dev.EcommerceUserService.dto.RoleResponseDto;
import com.dev.EcommerceUserService.dto.SignUpRequestDto;
import com.dev.EcommerceUserService.dto.UserResponseDto;
import com.dev.EcommerceUserService.model.Role;
import com.dev.EcommerceUserService.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.dev.EcommerceUserService.mapper.RoleMapper.toRoleResponseDto;

public class UserMapper {
    public static UserResponseDto toUserResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setName(user.getName());
        userResponseDto.setEmail(user.getEmail());
        if(user.getRoles()!=null) {
            List<RoleResponseDto> roleResponseDtos = new ArrayList<>();
            for (Role role : user.getRoles()) {
                roleResponseDtos.add(toRoleResponseDto(role));
            }
            userResponseDto.setRoles(Set.copyOf(roleResponseDtos));
        }
        return userResponseDto;
    }

    public static User toUser(SignUpRequestDto request) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return user;
    }
}
