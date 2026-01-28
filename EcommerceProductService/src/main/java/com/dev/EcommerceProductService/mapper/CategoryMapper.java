package com.dev.EcommerceProductService.mapper;

import com.dev.EcommerceProductService.dto.CategoryRequestDto;
import com.dev.EcommerceProductService.dto.CategoryResponseDto;
import com.dev.EcommerceProductService.model.Category;

public class CategoryMapper {
    public static Category toCategory(CategoryRequestDto categoryRequestDto)
    {
        Category category = new Category();
        category.setCategoryName(categoryRequestDto.getCategoryName());
        return category;
    }

    public static CategoryResponseDto toCategoryResponseDto(Category category)
    {
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setCategoryName(category.getCategoryName());
        categoryResponseDto.setCategoryId(String.valueOf(category.getId()));
        return categoryResponseDto;
    }

    public static CategoryResponseDto toCategoryResponseDto(String category)
    {
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setCategoryName(category);
        return categoryResponseDto;
    }
}
