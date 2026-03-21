package com.dev.ecommerceorderservice.client;

import com.dev.ecommerceorderservice.dto.ApiResponse;
import com.dev.ecommerceorderservice.dto.TokenValidationResponseDto;
import com.dev.ecommerceorderservice.dto.User;
import com.dev.ecommerceorderservice.exception.OrderServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class PaymentServiceClient {

    @Value("${paymentService.base.url}")
    private String paymentServiceBaseUrl;
    @Value("${paymentService.validate.api.url}")
    private String createPaymentLinkApiUrl;
    private RestTemplateBuilder restTemplateBuilder;

    public PaymentServiceClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public String createPaymentLink(Long orderId, User user) throws JsonProcessingException {
        ResponseEntity<ApiResponse<String>> response = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println("INSIDE createPaymentLink TOKEN");

            String url = paymentServiceBaseUrl + createPaymentLinkApiUrl;

            RestTemplate restTemplate = restTemplateBuilder.build();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);

            response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            requestEntity,
                            new ParameterizedTypeReference<ApiResponse<String>>() {
                            }, orderId
                    );
        }
        catch (HttpClientErrorException e) {
            System.out.println("qwertyuiop---------------"+e.getResponseBodyAsString());
            ApiResponse<String> apiResponse = mapper.readValue(
                            e.getResponseBodyAsString(),
                            new TypeReference<ApiResponse<String>>() {});
            throw new OrderServiceException(apiResponse.getResponse(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e) {
            System.out.println("qwertyuiop---------------"+e.getMessage());
            throw new OrderServiceException(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        System.out.println("qwertyuiop---------------"+response.getBody().getResponse());
        return response.getBody().getResponse();
    }
}
