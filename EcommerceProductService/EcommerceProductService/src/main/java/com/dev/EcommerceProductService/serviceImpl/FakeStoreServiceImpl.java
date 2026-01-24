package com.dev.EcommerceProductService.serviceImpl;

import com.dev.EcommerceProductService.client.FakeStoreClient;
import com.dev.EcommerceProductService.dto.FakeStoreProductRequestDto;
import com.dev.EcommerceProductService.dto.FakeStoreProductResponseDto;
import com.dev.EcommerceProductService.dto.ProductRequestDto;
import com.dev.EcommerceProductService.dto.ProductResponseDto;
import com.dev.EcommerceProductService.exception.ProductServiceException;
import com.dev.EcommerceProductService.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.dev.EcommerceProductService.mapper.ProductMapper.toFakeStoreProductRequestDto;
import static com.dev.EcommerceProductService.mapper.ProductMapper.toProductResponseDto;
import static com.dev.EcommerceProductService.util.ProductUtil.isNull;

@Service("FakeStoreServiceImpl")
public class FakeStoreServiceImpl implements ProductService {
    private FakeStoreClient fakeStoreClient;

    public FakeStoreServiceImpl(FakeStoreClient fakeStoreClient) {
        this.fakeStoreClient = fakeStoreClient;
    }

    @Override
    public List<ProductResponseDto> getAllProduct() {
        List<FakeStoreProductResponseDto> fakeStoreProductResponseDtos = fakeStoreClient.getAllProduct();
        if(isNull(fakeStoreProductResponseDtos) || fakeStoreProductResponseDtos.isEmpty()){
            throw new ProductServiceException("Products not found", HttpStatus.NOT_FOUND);
        }
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for (FakeStoreProductResponseDto fakeStoreProductResponseDto : fakeStoreProductResponseDtos) {
            productResponseDtos.add(toProductResponseDto(fakeStoreProductResponseDto));
        }
        return productResponseDtos;
    }

    @Override
    public ProductResponseDto getProductById(String productId) {
        FakeStoreProductResponseDto fakeStoreProductResponseDto = fakeStoreClient.getProductById(productId);
        if (isNull(fakeStoreProductResponseDto))
            throw new ProductServiceException("Product not found with id "+productId, HttpStatus.NOT_FOUND);
        return toProductResponseDto(fakeStoreProductResponseDto);
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        FakeStoreProductRequestDto fakeStoreProductRequestDto = toFakeStoreProductRequestDto(productRequestDto);
        FakeStoreProductResponseDto fakeStoreProductResponseDto = fakeStoreClient.createProduct(fakeStoreProductRequestDto);
        return toProductResponseDto(fakeStoreProductResponseDto);
    }

    @Override
    public ProductResponseDto updateProduct(String productId, ProductRequestDto productRequestDto) {
        FakeStoreProductRequestDto fakeStoreProductRequestDto = toFakeStoreProductRequestDto(productRequestDto);
        FakeStoreProductResponseDto fakeStoreProductResponseDto = fakeStoreClient.updateProduct(productId, fakeStoreProductRequestDto);
        return toProductResponseDto(fakeStoreProductResponseDto);
    }


    @Override
    public void deleteProduct(String productId) {
        fakeStoreClient.deleteProduct(productId);
    }

    @Override
    public List<ProductResponseDto> searchProducts(String name, String category, Double minPrice, Double maxPrice) {
        return List.of();
    }
}
