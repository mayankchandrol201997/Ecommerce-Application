package com.dev.EcommerceProductService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {
    private String id;
    private String title;
    private Double price;
    private String description;
    private CategoryResponseDto category;
    private String image;
}
