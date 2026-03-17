package com.dev.EcommerceProductService.service;

import com.dev.EcommerceProductService.dto.ProductResponseDto;
import com.dev.EcommerceProductService.dto.search.FilterDto;
import com.dev.EcommerceProductService.dto.search.SearchResponseDto;
import com.dev.EcommerceProductService.dto.search.SortingCriteria;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SearchService {
    public SearchResponseDto search
            (String query, List<FilterDto> filterDto, SortingCriteria sortingCriteria,int pageNumber, int pageSize);
}
