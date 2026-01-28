package com.dev.EcommerceProductService.controller;

import com.dev.EcommerceProductService.dto.CategoryResponseDto;
import com.dev.EcommerceProductService.dto.ProductRequestDto;
import com.dev.EcommerceProductService.dto.ProductResponseDto;
import com.dev.EcommerceProductService.exception.ProductServiceException;
import com.dev.EcommerceProductService.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean(name = "ProductServiceImpl")
    private ProductService productService;

    @Test
    public void testCreateProduct_Success() throws Exception
    {
        ProductRequestDto productRequestDto = mockProductRequestDto();
        ProductResponseDto productResponseDto = mockProductResponseDto();
        when(productService.createProduct(any(ProductRequestDto.class))).thenReturn(productResponseDto);

        mockMvc.perform(
                        post("/product")
                                .content(convertObjectToString(productRequestDto))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().string("{\"response\":{\"id\":\"f900d2f5-53c7-4da3-a2eb-d9665ca14622\",\"title\":\"ProductTitle\",\"price\":10.0,\"description\":\"ProductDescription\",\"category\":{\"categoryId\":\"f900d2f5-53c7-4da3-a2eb-d9665ca14622\",\"categoryName\":\"CategoryName\"},\"image\":\"ProductImage\"},\"message\":\"Success\"}"));
    }

    @Test
    public void testCreateProduct_CategoryNotFound() throws Exception
    {
        ProductRequestDto productRequestDto = mockProductRequestDto();
        when(productService.createProduct(any(ProductRequestDto.class))).thenThrow(
                new ProductServiceException("Category not found with Id " + productRequestDto.getCategoryId(), HttpStatus.NOT_FOUND));

        mockMvc.perform(
                        post("/product")
                                .content(convertObjectToString(productRequestDto))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound())
                .andExpect(content().string("{\"response\":\"Category not found with Id f900d2f5-53c7-4da3-a2eb-d9665ca14622\",\"message\":\"Failure\"}"));
    }

    @Test
    public void testUpdateProduct_Success() throws Exception
    {
        String productId = "f900d2f5-53c7-4da3-a2eb-d9665ca14622";
        ProductRequestDto productRequestDto = mockProductRequestDto();
        ProductResponseDto productResponseDto = mockProductResponseDto();
        when(productService.updateProduct(eq(productId),any(ProductRequestDto.class))).thenReturn(productResponseDto);

        mockMvc.perform(
                        put("/product/{id}",productId)
                                .content(convertObjectToString(productRequestDto))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().string("{\"response\":{\"id\":\"f900d2f5-53c7-4da3-a2eb-d9665ca14622\",\"title\":\"ProductTitle\",\"price\":10.0,\"description\":\"ProductDescription\",\"category\":{\"categoryId\":\"f900d2f5-53c7-4da3-a2eb-d9665ca14622\",\"categoryName\":\"CategoryName\"},\"image\":\"ProductImage\"},\"message\":\"Success\"}"));
    }

    @Test
    public void testUpdateProduct_CategoryNotFound() throws Exception
    {
        String productId = "f900d2f5-53c7-4da3-a2eb-d9665ca14622";
        ProductRequestDto productRequestDto = mockProductRequestDto();
        when(productService.updateProduct(eq(productId),any(ProductRequestDto.class))).thenThrow(
                new ProductServiceException("Category not found with Id " + productRequestDto.getCategoryId(), HttpStatus.NOT_FOUND));

        mockMvc.perform(
                        put("/product/{id}",productId)
                                .content(convertObjectToString(productRequestDto))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound())
                .andExpect(content().string("{\"response\":\"Category not found with Id f900d2f5-53c7-4da3-a2eb-d9665ca14622\",\"message\":\"Failure\"}"));
    }

    @Test
    public void testUpdateProduct_ProductNotFound() throws Exception
    {
        String productId = "f900d2f5-53c7-4da3-a2eb-d9665ca14622";
        ProductRequestDto productRequestDto = mockProductRequestDto();
        when(productService.updateProduct(eq(productId),any(ProductRequestDto.class))).thenThrow(
                new ProductServiceException("Product not found with Id " + productId, HttpStatus.NOT_FOUND));

        mockMvc.perform(
                        put("/product/{id}",productId)
                                .content(convertObjectToString(productRequestDto))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound())
                .andExpect(content().string("{\"response\":\"Product not found with Id f900d2f5-53c7-4da3-a2eb-d9665ca14622\",\"message\":\"Failure\"}"));
    }

    @Test
    public void testDeleteProduct_ProductNotFound() throws Exception
    {
        String productId = "f900d2f5-53c7-4da3-a2eb-d9665ca14622";
        ProductRequestDto productRequestDto = mockProductRequestDto();

        doThrow(new ProductServiceException(
                "Product not found with Id " + productId,
                HttpStatus.NOT_FOUND))
                .when(productService)
                .deleteProduct(productId);

        mockMvc.perform(
                        delete("/product/{id}",productId)
                ).andExpect(status().isNotFound())
                .andExpect(content().string("{\"response\":\"Product not found with Id f900d2f5-53c7-4da3-a2eb-d9665ca14622\",\"message\":\"Failure\"}"));
    }

    @Test
    public void testDeleteProduct_Success() throws Exception
    {
        String productId = "f900d2f5-53c7-4da3-a2eb-d9665ca14622";
        ProductRequestDto productRequestDto = mockProductRequestDto();

        doNothing().when(productService).deleteProduct(productId);

        mockMvc.perform(
                        delete("/product/{id}",productId)
                ).andExpect(status().isNoContent());
    }

    @Test
    public void testGetAllProducts_Success() throws Exception
    {
        List<ProductResponseDto> productResponseDto = List.of(mockProductResponseDto());
        when(productService.getAllProduct()).thenReturn(productResponseDto);

        mockMvc.perform(get("/product"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"response\":[{\"id\":\"f900d2f5-53c7-4da3-a2eb-d9665ca14622\",\"title\":\"ProductTitle\",\"price\":10.0,\"description\":\"ProductDescription\",\"category\":{\"categoryId\":\"f900d2f5-53c7-4da3-a2eb-d9665ca14622\",\"categoryName\":\"CategoryName\"},\"image\":\"ProductImage\"}],\"message\":\"Success\"}"));
    }

    @Test
    public void testGetAllProducts_NoProductsFound() throws Exception
    {
        when(productService.getAllProduct()).thenThrow(
                new ProductServiceException("Product not found.", HttpStatus.NOT_FOUND)
        );

        mockMvc.perform(get("/product"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetProduct_Success() throws Exception
    {
        ProductResponseDto productResponseDto = mockProductResponseDto();
        String productId = "f900d2f5-53c7-4da3-a2eb-d9665ca14622";
        when(productService.getProductById(productId)).thenReturn(productResponseDto);

        mockMvc.perform(
                        get("/product/{id}", "f900d2f5-53c7-4da3-a2eb-d9665ca14622")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{\"response\":{\"id\":\"f900d2f5-53c7-4da3-a2eb-d9665ca14622\",\"title\":\"ProductTitle\",\"price\":10.0,\"description\":\"ProductDescription\",\"category\":{\"categoryId\":\"f900d2f5-53c7-4da3-a2eb-d9665ca14622\",\"categoryName\":\"CategoryName\"},\"image\":\"ProductImage\"},\"message\":\"Success\"}"));
    }

    @Test
    public void testGetProduct_NoProductFound() throws Exception
    {
        String productId = "f900d2f5-53c7-4da3-a2eb-d9665ca14622";
        when(productService.getProductById(productId)).thenThrow(
                new ProductServiceException("Product not found with Id " + productId, HttpStatus.NOT_FOUND)
        );

        mockMvc.perform(
                        get("/product/{id}", "f900d2f5-53c7-4da3-a2eb-d9665ca14622")
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void searchProducts_success() throws Exception {
        List<ProductResponseDto> products = List.of(mockProductResponseDto());

        when(productService.searchProducts(
                eq("phone"),
                eq("Electronics"),
                eq(500.0),
                eq(1200.0)
        )).thenReturn(products);

        mockMvc.perform(get("/product/search")
                        .param("title", "phone")
                        .param("category", "Electronics")
                        .param("minPrice", "500")
                        .param("maxPrice", "1200"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").isArray())
                .andExpect(jsonPath("$.response.length()").value(1));
    }


    private String convertObjectToString(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    private ProductResponseDto mockProductResponseDto() {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId("f900d2f5-53c7-4da3-a2eb-d9665ca14622");
        productResponseDto.setCategory(mockCategoryResponseDto());
        productResponseDto.setDescription("ProductDescription");
        productResponseDto.setPrice(10.00);
        productResponseDto.setTitle("ProductTitle");
        productResponseDto.setImage("ProductImage");
        return productResponseDto;
    }

    private ProductRequestDto mockProductRequestDto() {
        ProductRequestDto productRequestDto = new ProductRequestDto();
        productRequestDto.setCategoryId("f900d2f5-53c7-4da3-a2eb-d9665ca14622");
        productRequestDto.setDescription("ProductDescription");
        productRequestDto.setPrice(10.00);
        productRequestDto.setTitle("ProductTitle");
        productRequestDto.setImage("ProductImage");
        return productRequestDto;
    }

    private CategoryResponseDto mockCategoryResponseDto()
    {
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setCategoryName("CategoryName");
        categoryResponseDto.setCategoryId("f900d2f5-53c7-4da3-a2eb-d9665ca14622");
        return categoryResponseDto;
    }
}
