package com.dev.EcommerceProductService.client;

import com.dev.EcommerceProductService.dto.ApiResponse;
import com.dev.EcommerceProductService.dto.TokenValidationResponseDto;
import com.dev.EcommerceProductService.exception.ProductServiceException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
public class UserServiceClient {

    @Value("${userservice.base.url}")
    private String userServiceBaseUrl;
    @Value("${userservice.validate.api.url}")
    private String userServiceValidateApiUrl;
    private RestTemplateBuilder restTemplateBuilder;

    public UserServiceClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public TokenValidationResponseDto validateToken(String token) {
        try {
            if (token == null || token.isBlank()) {
                throw new ProductServiceException("INVALID TOKEN", HttpStatus.BAD_REQUEST);
            }

            String url = userServiceBaseUrl + userServiceValidateApiUrl;

            RestTemplate restTemplate = restTemplateBuilder.build();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization",token);
            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<ApiResponse<TokenValidationResponseDto>> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            requestEntity,
                            new ParameterizedTypeReference<ApiResponse<TokenValidationResponseDto>>() {}
                    );

            ApiResponse<TokenValidationResponseDto> apiResponse = response.getBody();
            if (apiResponse == null || apiResponse.getResponse() == null) {
                throw new RuntimeException("Invalid response from user service");
            }
            return apiResponse.getResponse();
        } catch (HttpClientErrorException ex) {
            return buildErrorResponse("INVALID");

        } catch (HttpServerErrorException ex) {
            return buildErrorResponse("ERROR");
        }
    }

    private TokenValidationResponseDto buildErrorResponse(String status) {
        TokenValidationResponseDto response = new TokenValidationResponseDto();
        response.setStatus(status);
        return response;
    }
}
