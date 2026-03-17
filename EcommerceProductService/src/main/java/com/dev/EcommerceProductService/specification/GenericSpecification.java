package com.dev.EcommerceProductService.specification;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.*;

public class GenericSpecification<T> implements Specification<T> {

    private SearchCriteria criteria;

    public GenericSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<T> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder cb) {

        switch (criteria.getOperation()) {

            case EQUAL:
                return cb.equal(root.get(criteria.getKey()), criteria.getValue());

            case NOT_EQUAL:
                return cb.notEqual(root.get(criteria.getKey()), criteria.getValue());

            case LIKE:
                return cb.like(
                        cb.lower(root.get(criteria.getKey())),
                        "%" + criteria.getValue().toString().toLowerCase() + "%"
                );

            case GREATER_THAN:
                return cb.greaterThan(
                        root.get(criteria.getKey()),
                        criteria.getValue().toString()
                );

            case LESS_THAN:
                return cb.lessThan(
                        root.get(criteria.getKey()),
                        criteria.getValue().toString()
                );

            case GREATER_THAN_EQUAL:
                return cb.greaterThanOrEqualTo(
                        root.get(criteria.getKey()),
                        criteria.getValue().toString()
                );

            case LESS_THAN_EQUAL:
                return cb.lessThanOrEqualTo(
                        root.get(criteria.getKey()),
                        criteria.getValue().toString()
                );

            case IN:
                return root.get(criteria.getKey()).in(criteria.getValue());

            case BETWEEN:
                return cb.between(
                        root.get(criteria.getKey()),
                        (Comparable) criteria.getValue(),
                        (Comparable) criteria.getValueTo()
                );

            default:
                return null;
        }
    }
}