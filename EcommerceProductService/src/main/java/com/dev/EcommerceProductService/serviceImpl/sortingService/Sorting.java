package com.dev.EcommerceProductService.serviceImpl.sortingService;

import com.dev.EcommerceProductService.model.Product;

import java.util.List;

public interface Sorting {
    List<Product> sort(List<Product> products);
}
