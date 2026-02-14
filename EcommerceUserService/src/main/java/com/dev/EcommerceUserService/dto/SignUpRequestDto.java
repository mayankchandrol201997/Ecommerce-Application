package com.dev.EcommerceUserService.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {
    @NotBlank(message = "Name can't be empty")
    private String name;
    @NotBlank(message = "Email can't be empty")
    @Email(message = "Invalid Email")
    private String email;
    @NotBlank(message = "Password can't be empty")
    private String password;
}
