package com.dev.EcommerceProductService.controller;

import com.dev.EcommerceProductService.dto.ProductRequestDto;
import com.dev.EcommerceProductService.dto.ProductResponseDto;
import com.dev.EcommerceProductService.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

import static com.dev.EcommerceProductService.util.ProductUtil.buildResponseEntity;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(@Qualifier("ProductServiceImpl") ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<HashMap<String, Object>> createProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
        ProductResponseDto productResponseDto = productService.createProduct(productRequestDto);
        return new ResponseEntity<>(buildResponseEntity(productResponseDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> updateProduct(@Valid @RequestBody ProductRequestDto productRequestDto,
                                                                 @PathVariable("id") String productId) {
        ProductResponseDto productResponseDto = productService.updateProduct(productId, productRequestDto);
        return new ResponseEntity<>(buildResponseEntity(productResponseDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") String productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<HashMap<String, Object>> getAllProduct() {
        List<ProductResponseDto> productResponseDtoList = productService.getAllProduct();
        return new ResponseEntity<>(buildResponseEntity(productResponseDtoList), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> getProduct(@PathVariable("id") String productId) {
        ProductResponseDto productResponseDto = productService.getProductById(productId);
        return new ResponseEntity<>(buildResponseEntity(productResponseDto), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<HashMap<String, Object>> searchProducts(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        List<ProductResponseDto> productResponseDtoList = productService.searchProducts(title, category, minPrice, maxPrice);
        return new ResponseEntity<>(buildResponseEntity(productResponseDtoList), HttpStatus.OK);
    }
}
