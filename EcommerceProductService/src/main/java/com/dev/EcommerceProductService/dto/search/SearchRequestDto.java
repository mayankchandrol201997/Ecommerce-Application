package com.dev.EcommerceProductService.dto.search;

import com.dev.EcommerceProductService.dto.ProductResponseDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Getter
@Setter
public class SearchRequestDto {
    private String query;
    private List<FilterDto> filter;
    private SortingCriteria sortingCriteria;
    private int pageNumber;
    private int pageSize;
}
