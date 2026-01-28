package com.dev.EcommerceProductService.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CategoryResponseDto {
    private String categoryId;
    private String categoryName;
}
