package com.dev.EcommerceProductService.serviceImpl;

import com.dev.EcommerceProductService.dto.CategoryRequestDto;
import com.dev.EcommerceProductService.dto.CategoryResponseDto;
import com.dev.EcommerceProductService.exception.ProductServiceException;
import com.dev.EcommerceProductService.mapper.CategoryMapper;
import com.dev.EcommerceProductService.model.Category;
import com.dev.EcommerceProductService.repository.CategoryRepository;
import com.dev.EcommerceProductService.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.dev.EcommerceProductService.mapper.CategoryMapper.toCategory;
import static com.dev.EcommerceProductService.mapper.CategoryMapper.toCategoryResponseDto;
import static com.dev.EcommerceProductService.util.ProductUtil.toUUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryResponseDto> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();

        if (categories.isEmpty()) {
            throw new ProductServiceException("Category not found", HttpStatus.NOT_FOUND);
        }

        return categories.stream()
                .map(CategoryMapper::toCategoryResponseDto)
                .toList();
    }

    @Override
    public CategoryResponseDto getCategoryById(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                ()-> new ProductServiceException("Category not found with Id "+categoryId, HttpStatus.NOT_FOUND)
        );
        return toCategoryResponseDto(category);
    }

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        Category category = toCategory(categoryRequestDto);
        category = categoryRepository.save(category);
        return toCategoryResponseDto(category);
    }

    @Override
    public CategoryResponseDto updateCategory(UUID categoryId, CategoryRequestDto categoryRequestDto) {
        if(!categoryRepository.existsById(categoryId)){
            throw new ProductServiceException("Category not found with Id "+categoryId, HttpStatus.NOT_FOUND);
        }
        Category category = toCategory(categoryRequestDto);
        category.setId(categoryId);
        category = categoryRepository.save(category);
        return toCategoryResponseDto(category);
    }

    @Override
    public void deleteCategory(UUID categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ProductServiceException("Category not found with Id " + categoryId, HttpStatus.NOT_FOUND);
        }
        categoryRepository.deleteById(categoryId);
    }
}
