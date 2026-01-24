package com.dev.EcommerceProductService.service;

import com.dev.EcommerceProductService.dto.CategoryRequestDto;
import com.dev.EcommerceProductService.dto.CategoryResponseDto;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<CategoryResponseDto> getAllCategory();
    CategoryResponseDto getCategoryById(UUID categoryId);
    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);
    CategoryResponseDto updateCategory(UUID categoryId, CategoryRequestDto categoryRequestDto);
    void deleteCategory(UUID categoryId);
}
