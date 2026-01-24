package com.dev.EcommerceProductService.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {
    @NotBlank(message = "Title can't be empty")
    private String title;
    @NotNull(message = "Price can't be null")
    @Min(value = 1)
    private Double price;
    @NotBlank(message = "Description can't be empty")
    private String description;
    @NotBlank(message = "Category can't be empty")
    private String categoryId;
    @NotBlank(message = "Image can't be empty")
    private String image;
}
