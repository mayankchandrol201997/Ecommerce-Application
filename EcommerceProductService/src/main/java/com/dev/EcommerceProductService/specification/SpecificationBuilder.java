package com.dev.EcommerceProductService.specification;

import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class SpecificationBuilder<T> {

    private List<SearchCriteria> params = new ArrayList<>();

    public SpecificationBuilder<T> with(
            String key,
            Object value,
            SearchOperation operation) {

        params.add(new SearchCriteria(key, value, operation));
        return this;
    }

    public SpecificationBuilder<T> withBetween(
            String key,
            Object value,
            Object valueTo) {

        params.add(new SearchCriteria(key, value, valueTo, SearchOperation.BETWEEN));
        return this;
    }

    public Specification<T> build() {

        if (params.isEmpty()) {
            return null;
        }

        List<Specification<T>> specs = new ArrayList<>();

        for (SearchCriteria param : params) {
            specs.add(new GenericSpecification<>(param));
        }

        Specification<T> result = specs.get(0);

        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }

        return result;
    }
}