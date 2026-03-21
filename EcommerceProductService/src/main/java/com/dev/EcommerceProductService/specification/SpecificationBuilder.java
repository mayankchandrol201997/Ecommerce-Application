package com.dev.EcommerceProductService.specification;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SpecificationBuilder<T> {

    private List<SearchCriteria> searchCriteria = new ArrayList<>();

    public Specification<T> build() {

        if (searchCriteria.isEmpty()) {
            return null;
        }

        List<Specification<T>> specs = new ArrayList<>();

        for (SearchCriteria param : searchCriteria) {
            specs.add(new GenericSpecification<>(param));
        }

        Specification<T> result = specs.get(0);

        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }

        return result;
    }
}