package com.dev.EcommerceProductService.dto.search;

import com.dev.EcommerceProductService.dto.ProductResponseDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class SearchResponseDto {
    private Page<ProductResponseDto> productResponseDto;
}
