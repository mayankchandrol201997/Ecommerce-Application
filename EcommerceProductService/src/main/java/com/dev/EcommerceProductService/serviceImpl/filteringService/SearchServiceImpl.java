package com.dev.EcommerceProductService.serviceImpl.filteringService;

import com.dev.EcommerceProductService.dto.ProductResponseDto;
import com.dev.EcommerceProductService.dto.search.FilterDto;
import com.dev.EcommerceProductService.dto.search.SearchResponseDto;
import com.dev.EcommerceProductService.dto.search.SortingCriteria;
import com.dev.EcommerceProductService.model.Product;
import com.dev.EcommerceProductService.repository.ProductRepository;
import com.dev.EcommerceProductService.service.SearchService;
import com.dev.EcommerceProductService.serviceImpl.sortingService.Sorting;
import com.dev.EcommerceProductService.serviceImpl.sortingService.SorterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.dev.EcommerceProductService.mapper.ProductMapper.toProductResponseDto;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public SearchResponseDto search(String query, List<FilterDto> filterDtos,
                                           SortingCriteria sortingCriteria,
                                           int pageNumber, int pageSize) {
        List<Product> products = productRepository.findProductByTitleContaining(query);

        for (FilterDto filterDto : filterDtos) {
            Filters filter = FilterFactory.getFilter(filterDto.getKey());
            if (filter != null) {
                products = filter.apply(products, filterDto.getValues());
            }
        }

        Sorting sorting = SorterFactory.getSorterByCriteria(sortingCriteria);
        products = sorting.sort(products);

        List<ProductResponseDto> productOnPage = new ArrayList<>();

        int start = (pageNumber - 1) * pageSize;
        int end = Math.min(start + pageSize, products.size());

        for (int i = start; i < end; i++) {
            productOnPage.add(toProductResponseDto(products.get(i)));
        }

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<ProductResponseDto> productResponseDtoPage =
                new PageImpl<>(productOnPage, pageable, products.size());

        SearchResponseDto searchResponseDto = new SearchResponseDto();
        searchResponseDto.setProductResponseDto(productResponseDtoPage);

        return searchResponseDto;
    }
}
