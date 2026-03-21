package com.dev.EcommerceProductService.dto;

import com.dev.EcommerceProductService.specification.SearchCriteria;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpecificationSearchRequestDTO {
    private List<SearchCriteria> searchCriteria;
}