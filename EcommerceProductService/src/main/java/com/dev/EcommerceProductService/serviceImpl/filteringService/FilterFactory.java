package com.dev.EcommerceProductService.serviceImpl.filteringService;

public class FilterFactory {

    public static Filters getFilter(String key){
        System.out.println(key);
        return switch (key){

            case "brand" ->  new BrandFilter();
            default -> null;
        };
    }
}
