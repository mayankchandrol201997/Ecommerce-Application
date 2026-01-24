package com.dev.EcommerceProductService.serviceImpl;

import com.dev.EcommerceProductService.dto.CategoryResponseDto;
import com.dev.EcommerceProductService.dto.ProductRequestDto;
import com.dev.EcommerceProductService.dto.ProductResponseDto;
import com.dev.EcommerceProductService.exception.ProductServiceException;
import com.dev.EcommerceProductService.mapper.ProductMapper;
import com.dev.EcommerceProductService.model.Category;
import com.dev.EcommerceProductService.model.Product;
import com.dev.EcommerceProductService.repository.CategoryRepository;
import com.dev.EcommerceProductService.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.dev.EcommerceProductService.mapper.ProductMapper.toProduct;
import static com.dev.EcommerceProductService.mapper.ProductMapper.toProductResponseDto;
import static com.dev.EcommerceProductService.util.ProductUtil.toUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProductSuccess() {
        ProductRequestDto productRequestDto = mockProductRequestDto();
        UUID categoryUuid = UUID.fromString(productRequestDto.getCategoryId());

        Category category = mockCategory();
        Product product = mockProduct();
        ProductResponseDto productResponseDto = mockProductResponseDto();

        try (MockedStatic<ProductMapper> mockedStatic = mockStatic(ProductMapper.class)) {
            mockedStatic.when(() -> toProductResponseDto(product)).thenReturn(productResponseDto);
            mockedStatic.when(() -> toProduct(productRequestDto)).thenReturn(product);

            when(categoryRepository.findById(categoryUuid)).thenReturn(Optional.of(category));

            when(productRepository.save(product)).thenReturn(product);

            ProductResponseDto response = productService.createProduct(productRequestDto);

            assertNotNull(response);
        }
    }

    @Test
    public void testCreateProduct_CategoryNotFound() {
        ProductRequestDto productRequestDto = mockProductRequestDto();
        UUID categoryUuid = UUID.fromString(productRequestDto.getCategoryId());

        when(categoryRepository.findById(categoryUuid)).thenReturn(Optional.empty());

        ProductServiceException exception = assertThrows(ProductServiceException.class, () -> productService.createProduct(productRequestDto));

        assertEquals("Category not found with Id " + categoryUuid, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    public void testGetProductByIdSuccess() {
        UUID productUUID = UUID.randomUUID();
        String productId = String.valueOf(productUUID);
        Product product = mockProduct();
        ProductResponseDto productResponseDto = mockProductResponseDto();

        try (MockedStatic<ProductMapper> mockedStatic = Mockito.mockStatic(ProductMapper.class)) {
            when(productRepository.findById(productUUID)).thenReturn(Optional.of(product));
            mockedStatic.when(() -> toProductResponseDto(product)).thenReturn(productResponseDto);

            ProductResponseDto response = productService.getProductById(productId);
            assertNotNull(response);
        }
    }

    @Test
    public void testGetProductById_ProductNotFound() {
        UUID productUUID = UUID.randomUUID();
        String productId = String.valueOf(productUUID);

        when(productRepository.findById(productUUID)).thenReturn(Optional.empty());

        ProductServiceException productServiceException = assertThrows(ProductServiceException.class, () -> productService.getProductById(productId));

        assertEquals("Product not found with Id " + productId, productServiceException.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, productServiceException.getHttpStatus());
    }

    @Test
    public void testGetAllProductsSuccess() {
        List<Product> product = List.of(mockProduct());
        ProductResponseDto productResponseDto = mockProductResponseDto();

        try (MockedStatic<ProductMapper> mockedStatic = Mockito.mockStatic(ProductMapper.class)) {
            mockedStatic.when(() -> toProductResponseDto(any(Product.class))).thenReturn(productResponseDto);
            when(productRepository.findAll()).thenReturn(product);

            List<ProductResponseDto> response = productService.getAllProduct();
            assertEquals(response.size(), product.size());
        }
    }

    @Test
    public void testGetAllProducts_ProductNotFound() {
        when(productRepository.findAll()).thenReturn(List.of());

        ProductServiceException productServiceException = assertThrows(ProductServiceException.class, () -> productService.getAllProduct());

        assertEquals("Product not found.", productServiceException.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, productServiceException.getHttpStatus());
    }

    @Test
    public void testUpdateProductSuccess() {
        UUID productUUID = UUID.randomUUID();
        String productId = String.valueOf(productUUID);
        ProductRequestDto productRequestDto = mockProductRequestDto();
        UUID categoryId = toUUID(productRequestDto.getCategoryId());
        Product product = mockProduct();
        ProductResponseDto productResponseDto = mockProductResponseDto();

        try (MockedStatic<ProductMapper> mockedStatic = Mockito.mockStatic(ProductMapper.class)) {
            mockedStatic.when(() -> toProduct(productRequestDto)).thenReturn(product);
            mockedStatic.when(() -> toProductResponseDto(product)).thenReturn(productResponseDto);
            when(productRepository.existsById(productUUID)).thenReturn(true);
            when(categoryRepository.existsById(categoryId)).thenReturn(true);
            when(productRepository.save(product)).thenReturn(product);

            ProductResponseDto response = productService.updateProduct(productId, productRequestDto);
            assertNotNull(response);
        }
    }

    @Test
    public void testUpdateProduct_ProductNotFound() {
        UUID productUUID = UUID.randomUUID();
        String productId = String.valueOf(productUUID);
        ProductRequestDto productRequestDto = mockProductRequestDto();

        when(productRepository.existsById(productUUID)).thenReturn(false);

        ProductServiceException productServiceException = assertThrows(ProductServiceException.class, () -> productService.updateProduct(productId, productRequestDto));

        assertEquals("Product not found with Id " + productId, productServiceException.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, productServiceException.getHttpStatus());
    }

    @Test
    public void testUpdateProduct_CategoryNotFound() {
        UUID productUUID = UUID.randomUUID();
        String productId = String.valueOf(productUUID);
        ProductRequestDto productRequestDto = mockProductRequestDto();
        UUID categoryId = toUUID(productRequestDto.getCategoryId());

        when(productRepository.existsById(productUUID)).thenReturn(true);
        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        ProductServiceException productServiceException = assertThrows(ProductServiceException.class, () -> productService.updateProduct(productId, productRequestDto));

        assertEquals("Category not found with Id " + categoryId, productServiceException.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, productServiceException.getHttpStatus());
    }

    @Test
    public void deleteProductSuccess() {
        UUID productUUID = UUID.randomUUID();
        String productId = String.valueOf(productUUID);

        when(productRepository.existsById(productUUID)).thenReturn(true);

        productService.deleteProduct(productId);
        verify(productRepository, times(1)).existsById(productUUID);
        verify(productRepository, times(1)).deleteById(productUUID);
    }

    @Test
    public void deleteProduct_ProductNotFound() {
        UUID productUUID = UUID.randomUUID();
        String productId = String.valueOf(productUUID);

        when(productRepository.existsById(productUUID)).thenReturn(false);

        ProductServiceException productServiceException = assertThrows(ProductServiceException.class, () -> productService.deleteProduct(productId));

        assertEquals("Product not found with Id " + productId, productServiceException.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, productServiceException.getHttpStatus());
        verify(productRepository, times(1)).existsById(productUUID);
        verify(productRepository, times(0)).deleteById(productUUID);
    }

    @Test
    public void searchProduct() {
        String title = "title";
        String category = "category";
        double minPrice = 1;
        double maxPrice = 100;
        Product product = mockProduct();
        List<Product> products = List.of(product);
        ProductResponseDto productResponseDto = mockProductResponseDto();

        try (MockedStatic<ProductMapper> mockedStatic = Mockito.mockStatic(ProductMapper.class)) {
            mockedStatic.when(() -> toProductResponseDto(product)).thenReturn(productResponseDto);
            when(productRepository.findAll(any(Specification.class))).thenReturn(products);

            List<ProductResponseDto> response = productService.searchProducts(title, category, minPrice, maxPrice);

            assertNotNull(response);
            assertEquals(1, response.size());
            assertEquals(productResponseDto, response.get(0));
        }
    }

    @Test
    public void searchProduct_ProductNotFound() {
        String title = "title";
        String category = "category";
        double minPrice = 1;
        double maxPrice = 100;

        when(productRepository.findAll(any(Specification.class))).thenReturn(List.of());

        ProductServiceException productServiceException = assertThrows(ProductServiceException.class, () -> productService.searchProducts(title, category, minPrice, maxPrice));

        assertEquals("Product not found.", productServiceException.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, productServiceException.getHttpStatus());
    }


    private Product mockProduct() {
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setDescription("product Description");
        product.setTitle("product Title");
        product.setPrice(10.00);
        Category category = mockCategory();
        category.setId(UUID.randomUUID());
        product.setCategory(category);
        product.setImage("product Image");
        return product;
    }

    private ProductResponseDto mockProductResponseDto() {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setDescription("product Description");
        productResponseDto.setTitle("product Title");
        productResponseDto.setPrice(100.00);
        CategoryResponseDto categoryResponseDto = mockCategoryResponseDto();
        productResponseDto.setCategory(categoryResponseDto);
        productResponseDto.setImage("product Image");
        return productResponseDto;
    }

    private ProductRequestDto mockProductRequestDto() {
        ProductRequestDto productRequestDto = new ProductRequestDto();
        productRequestDto.setDescription("product Description");
        productRequestDto.setTitle("product Title");
        productRequestDto.setPrice(100.00);
        productRequestDto.setCategoryId(UUID.randomUUID().toString());
        productRequestDto.setImage("product Image");
        return productRequestDto;
    }

    private Category mockCategory() {
        Category category = new Category();
        category.setId(UUID.randomUUID());
        category.setCategoryName("Category Name");
        return category;
    }

    private CategoryResponseDto mockCategoryResponseDto() {
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setCategoryName("Category Name");
        return categoryResponseDto;
    }
}
