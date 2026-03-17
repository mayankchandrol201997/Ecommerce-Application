package com.dev.EcommerceProductService.client;

import com.dev.EcommerceProductService.dto.TokenValidationResponseDto;
import com.dev.EcommerceProductService.exception.ProductServiceException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
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
            System.out.println("INSIDE VALIDATE TOKEN");

            if (token == null || token.isBlank()) {
                throw new ProductServiceException("INVALID TOKEN", HttpStatus.BAD_REQUEST);
            }

            String url = userServiceBaseUrl + userServiceValidateApiUrl;

            RestTemplate restTemplate = restTemplateBuilder.build();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization",token);

            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<String> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            requestEntity,
                            String.class
                    );
            System.out.println("INSIDE VALIDATE TOKEN----"+response.getBody());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseBody = objectMapper.readTree(response.getBody());
            return objectMapper.readValue(
                    responseBody.get("response").toString(), TokenValidationResponseDto.class);
        } catch (HttpClientErrorException ex) {
            System.err.println("Token validation failed: " + ex.getResponseBodyAsString());
            TokenValidationResponseDto errorResponse = new TokenValidationResponseDto();
            errorResponse.setStatus("INVALID");
            return errorResponse;

        } catch (Exception ex) {
            System.err.println("Unexpected error: " + ex.getMessage());
            TokenValidationResponseDto errorResponse = new TokenValidationResponseDto();
            errorResponse.setStatus("ERROR");
            return errorResponse;
        }
    }
}
