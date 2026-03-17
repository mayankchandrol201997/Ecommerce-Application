package com.dev.EcommerceProductService.serviceImpl.sortingService;

import com.dev.EcommerceProductService.model.Product;

import java.util.List;

public class PriceLowToHighSorting implements Sorting {
    @Override
    public List<Product> sort(List<Product> products) {
        return products;
    }
}
