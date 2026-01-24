package com.dev.EcommerceProductService.serviceImpl;

import com.dev.EcommerceProductService.dto.ProductRequestDto;
import com.dev.EcommerceProductService.dto.ProductResponseDto;
import com.dev.EcommerceProductService.exception.ProductServiceException;
import com.dev.EcommerceProductService.mapper.ProductMapper;
import com.dev.EcommerceProductService.model.Category;
import com.dev.EcommerceProductService.model.Product;
import com.dev.EcommerceProductService.repository.CategoryRepository;
import com.dev.EcommerceProductService.repository.ProductRepository;
import com.dev.EcommerceProductService.service.ProductService;
import com.dev.EcommerceProductService.specification.ProductSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.dev.EcommerceProductService.mapper.ProductMapper.toProduct;
import static com.dev.EcommerceProductService.mapper.ProductMapper.toProductResponseDto;
import static com.dev.EcommerceProductService.util.ProductUtil.toUUID;

@Service("ProductServiceImpl")
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ProductResponseDto> getAllProduct() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) throw new ProductServiceException("Product not found.", HttpStatus.NOT_FOUND);
        return products.stream().map(ProductMapper::toProductResponseDto).toList();
    }

    @Override
    public ProductResponseDto getProductById(String productId) {
        Product product = productRepository.findById(toUUID(productId)).orElseThrow(() -> new ProductServiceException("Product not found with Id " + productId, HttpStatus.NOT_FOUND));
        return toProductResponseDto(product);
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        String categoryId = productRequestDto.getCategoryId();
        Category category = categoryRepository.findById(toUUID(categoryId)).orElseThrow(() -> new ProductServiceException("Category not found with Id " + categoryId, HttpStatus.NOT_FOUND));
        Product product = toProduct(productRequestDto);
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return toProductResponseDto(savedProduct);
    }

    @Override
    public ProductResponseDto updateProduct(String productId, ProductRequestDto productRequestDto) {
        String categoryId = productRequestDto.getCategoryId();
        if (!productRepository.existsById(toUUID(productId))) {
            throw new ProductServiceException("Product not found with Id " + productId, HttpStatus.NOT_FOUND);
        }
        if (!categoryRepository.existsById(toUUID(categoryId))) {
            throw new ProductServiceException("Category not found with Id " + categoryId, HttpStatus.NOT_FOUND);
        }
        Product product = toProduct(productRequestDto);
        product.setId(toUUID(productId));
        product = productRepository.save(product);
        return toProductResponseDto(product);
    }

    @Override
    public void deleteProduct(String productId) {
        if (!productRepository.existsById(toUUID(productId))) {
            throw new ProductServiceException("Product not found with Id " + productId, HttpStatus.NOT_FOUND);
        }
        productRepository.deleteById(toUUID(productId));
    }

    @Override
    public List<ProductResponseDto> searchProducts(String title, String category, Double minPrice, Double maxPrice) {
        Specification<Product> spec = Specification.where(ProductSpecification.hasTitle(title))
                .and(ProductSpecification.hasCategory(category))
                .and(ProductSpecification
                        .priceBetween(minPrice, maxPrice));
        List<Product> products = productRepository.findAll(spec);
        if (products.isEmpty()) throw new ProductServiceException("Product not found.", HttpStatus.NOT_FOUND);
        return products.stream().map(ProductMapper::toProductResponseDto).toList();
    }
}
