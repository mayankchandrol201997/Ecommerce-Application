package com.dev.EcommerceUserService.security.service;

import com.dev.EcommerceUserService.model.User;
import com.dev.EcommerceUserService.repository.UserRepository;
import com.dev.EcommerceUserService.security.model.CustomUserDetail;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@JsonDeserialize
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if(optionalUser.isEmpty())
            throw new UsernameNotFoundException("Username doesn't exist");

        return new CustomUserDetail(optionalUser.get());
    }
}
