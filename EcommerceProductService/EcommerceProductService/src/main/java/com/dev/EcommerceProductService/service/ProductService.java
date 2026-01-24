package com.dev.EcommerceProductService.service;

import com.dev.EcommerceProductService.dto.ProductRequestDto;
import com.dev.EcommerceProductService.dto.ProductResponseDto;

import java.util.List;

public interface ProductService {
    List<ProductResponseDto> getAllProduct();

    ProductResponseDto getProductById(String productId);

    ProductResponseDto createProduct(ProductRequestDto productRequestDto);

    ProductResponseDto updateProduct(String productId, ProductRequestDto productRequestDto);

    void deleteProduct(String productId);

    List<ProductResponseDto> searchProducts(String name, String category, Double minPrice, Double maxPrice);
}
