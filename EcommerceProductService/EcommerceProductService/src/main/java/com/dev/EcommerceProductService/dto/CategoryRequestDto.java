package com.dev.EcommerceProductService.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequestDto {
    @NotBlank(message = "categoryName can't be empty.")
    private String categoryName;
}
