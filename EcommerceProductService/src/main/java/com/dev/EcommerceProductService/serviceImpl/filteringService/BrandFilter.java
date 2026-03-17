package com.dev.EcommerceProductService.serviceImpl.filteringService;

import com.dev.EcommerceProductService.model.Product;

import java.util.List;

public class BrandFilter implements Filters {
    @Override
    public List<Product> apply(List<Product> productList,List<String> allowedValues) {
        return productList;
    }
}
