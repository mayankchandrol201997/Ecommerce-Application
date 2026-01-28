package com.dev.EcommerceProductService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductResponseDto {
    private String id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
}
