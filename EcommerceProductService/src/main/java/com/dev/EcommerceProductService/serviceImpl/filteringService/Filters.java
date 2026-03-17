package com.dev.EcommerceProductService.serviceImpl.filteringService;

import com.dev.EcommerceProductService.model.Product;

import java.util.List;

public interface Filters {
    List<Product> apply(List<Product> productList,List<String> allowedValues);
}
