package com.dev.EcommerceProductService.controller;

import com.dev.EcommerceProductService.dto.CategoryRequestDto;
import com.dev.EcommerceProductService.dto.CategoryResponseDto;
import com.dev.EcommerceProductService.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.dev.EcommerceProductService.util.ProductUtil.buildResponseEntity;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<HashMap<String,Object>> createCategory(@RequestBody CategoryRequestDto CategoryRequestDto) {
        CategoryResponseDto categoryResponseDto = categoryService.createCategory(CategoryRequestDto);
        return new ResponseEntity<>(buildResponseEntity(categoryResponseDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HashMap<String,Object>> updateCategory(@PathVariable("id") UUID CategoryId,
                                                              @RequestBody CategoryRequestDto CategoryRequestDto) {
        CategoryResponseDto CategoryResponseDto = categoryService.updateCategory(CategoryId, CategoryRequestDto);
        return new ResponseEntity<>(buildResponseEntity(CategoryResponseDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") UUID CategoryId) {
        categoryService.deleteCategory(CategoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<HashMap<String,Object>> getAllCategory() {
        List<CategoryResponseDto> CategoryResponseDtoList = categoryService.getAllCategory();
        return new ResponseEntity<>(buildResponseEntity(CategoryResponseDtoList), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String,Object>> getCategory(@PathVariable("id") UUID CategoryId) {
        CategoryResponseDto CategoryResponseDto = categoryService.getCategoryById(CategoryId);
        return new ResponseEntity<>(buildResponseEntity(CategoryResponseDto), HttpStatus.OK);
    }
}
