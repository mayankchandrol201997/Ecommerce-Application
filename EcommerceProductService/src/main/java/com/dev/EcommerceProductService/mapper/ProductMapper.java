package com.dev.EcommerceProductService.mapper;

import com.dev.EcommerceProductService.dto.FakeStoreProductRequestDto;
import com.dev.EcommerceProductService.dto.FakeStoreProductResponseDto;
import com.dev.EcommerceProductService.dto.ProductRequestDto;
import com.dev.EcommerceProductService.dto.ProductResponseDto;
import com.dev.EcommerceProductService.model.Category;
import com.dev.EcommerceProductService.model.Product;

import static com.dev.EcommerceProductService.mapper.CategoryMapper.toCategoryResponseDto;
import static com.dev.EcommerceProductService.util.ProductUtil.isNull;
import static com.dev.EcommerceProductService.util.ProductUtil.toUUID;

public class ProductMapper {
    public static FakeStoreProductRequestDto toFakeStoreProductRequestDto(ProductRequestDto productRequestDto)
    {
        FakeStoreProductRequestDto fakeStoreProductRequestDto = new FakeStoreProductRequestDto();
        fakeStoreProductRequestDto.setCategory(productRequestDto.getCategoryId());
        fakeStoreProductRequestDto.setDescription(productRequestDto.getDescription());
        fakeStoreProductRequestDto.setImage(productRequestDto.getImage());
        fakeStoreProductRequestDto.setTitle(productRequestDto.getTitle());
        fakeStoreProductRequestDto.setPrice(productRequestDto.getPrice());
        return fakeStoreProductRequestDto;
    }

    public static ProductResponseDto toProductResponseDto(FakeStoreProductResponseDto fakeStoreProductResponseDto){
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(fakeStoreProductResponseDto.getId());
        productResponseDto.setCategory(toCategoryResponseDto(fakeStoreProductResponseDto.getCategory()));
        productResponseDto.setDescription(fakeStoreProductResponseDto.getDescription());
        productResponseDto.setPrice(fakeStoreProductResponseDto.getPrice());
        productResponseDto.setTitle(fakeStoreProductResponseDto.getTitle());
        productResponseDto.setImage(fakeStoreProductResponseDto.getImage());
        return productResponseDto;
    }

    public static Product toProduct(ProductRequestDto productRequestDto)
    {
        Product product = new Product();
        Category category = new Category();
        category.setId(toUUID(productRequestDto.getCategoryId()));
        product.setCategory(category);
        product.setDescription(productRequestDto.getDescription());
        product.setImage(productRequestDto.getImage());
        product.setTitle(productRequestDto.getTitle());
        product.setPrice(productRequestDto.getPrice());
        return product;
    }

    public static ProductResponseDto toProductResponseDto(Product product){
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(String.valueOf(product.getId()));
        productResponseDto.setCategory(toCategoryResponseDto(product.getCategory()));
        productResponseDto.setDescription(product.getDescription());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setTitle(product.getTitle());
        productResponseDto.setImage(product.getImage());
        return productResponseDto;
    }
}
