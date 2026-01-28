package com.dev.EcommerceProductService.client;

import com.dev.EcommerceProductService.dto.FakeStoreProductRequestDto;
import com.dev.EcommerceProductService.dto.FakeStoreProductResponseDto;
import com.dev.EcommerceProductService.dto.ProductRequestDto;
import com.dev.EcommerceProductService.dto.ProductResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Component
public class FakeStoreClient {

    @Value("${fakestore.api.url}")
    private String fakeStoreApiUrl;
    @Value("${fakestore.api.path.product}")
    private String fakeStoreApiPathProduct;
    private RestTemplateBuilder restTemplateBuilder;

    public FakeStoreClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public List<FakeStoreProductResponseDto> getAllProduct() {
        System.out.println(fakeStoreApiUrl);
        System.out.println(fakeStoreApiPathProduct);
        String url = fakeStoreApiUrl + fakeStoreApiPathProduct;
        System.out.println(url);
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductResponseDto[]> fakeStoreProductResponseDtoS =
                restTemplate.getForEntity(url, FakeStoreProductResponseDto[].class);
        return List.of(Objects.requireNonNull(fakeStoreProductResponseDtoS.getBody()));
    }

    public FakeStoreProductResponseDto getProductById(String productId) {
        String url = fakeStoreApiUrl + fakeStoreApiPathProduct + "/{id}";
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductResponseDto> productResponseDto =
                restTemplate.getForEntity(url, FakeStoreProductResponseDto.class, productId);
        return productResponseDto.getBody();
    }

    public FakeStoreProductResponseDto createProduct(FakeStoreProductRequestDto fakeStoreProductRequestDto) {
        String url = fakeStoreApiUrl + fakeStoreApiPathProduct;
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductResponseDto> fakeStoreProductResponseDto =
                restTemplate.postForEntity(url, fakeStoreProductRequestDto, FakeStoreProductResponseDto.class);
        return fakeStoreProductResponseDto.getBody();
    }

    public FakeStoreProductResponseDto updateProduct(String productId, FakeStoreProductRequestDto fakeStoreProductRequestDto) {
        String url = fakeStoreApiUrl + fakeStoreApiPathProduct + "/{id}";
        RestTemplate restTemplate = restTemplateBuilder.build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<FakeStoreProductRequestDto> requestEntity =
                new HttpEntity<>(fakeStoreProductRequestDto, headers);

        ResponseEntity<FakeStoreProductResponseDto> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.PUT,
                        requestEntity,
                        FakeStoreProductResponseDto.class,
                        productId
                );

        return response.getBody();
    }


    public boolean deleteProduct(String productId) {
        String url = fakeStoreApiUrl + fakeStoreApiPathProduct + "/" + productId;
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.delete(url, productId);
        return true;
    }
}
