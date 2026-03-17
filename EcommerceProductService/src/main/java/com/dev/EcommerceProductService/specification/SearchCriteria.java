package com.dev.EcommerceProductService.specification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCriteria {

    private String key;
    private Object value;
    private Object valueTo;
    private SearchOperation operation;

    public SearchCriteria(String key, Object value, SearchOperation operation) {
        this.key = key;
        this.value = value;
        this.operation = operation;
    }

    public SearchCriteria(String key, Object value, Object valueTo, SearchOperation operation) {
        this.key = key;
        this.value = value;
        this.valueTo = valueTo;
        this.operation = operation;
    }

    // getters
}