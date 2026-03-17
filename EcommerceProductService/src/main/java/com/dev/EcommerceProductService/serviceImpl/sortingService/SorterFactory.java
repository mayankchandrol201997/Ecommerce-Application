package com.dev.EcommerceProductService.serviceImpl.sortingService;

import com.dev.EcommerceProductService.dto.search.SortingCriteria;

public class SorterFactory {
    public static Sorting getSorterByCriteria(SortingCriteria sortingCriteria)
    {
        return switch (sortingCriteria){
            case PRICE_LOW_TO_HIGH ->  new PriceLowToHighSorting();
            case PRICE_HIGH_TO_LOW ->  new PriceHighToLowSorting();
            default -> null;
        };
    }
}
