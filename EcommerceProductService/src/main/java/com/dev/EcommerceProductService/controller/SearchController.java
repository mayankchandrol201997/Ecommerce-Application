package com.dev.EcommerceProductService.controller;

import com.dev.EcommerceProductService.dto.ProductResponseDto;
import com.dev.EcommerceProductService.dto.search.FilterDto;
import com.dev.EcommerceProductService.dto.search.SearchRequestDto;
import com.dev.EcommerceProductService.dto.search.SearchResponseDto;
import com.dev.EcommerceProductService.dto.search.SortingCriteria;
import com.dev.EcommerceProductService.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping
    public ResponseEntity<SearchResponseDto> search(@RequestParam("query") String query,
                                                    @ModelAttribute SearchRequestDto searchRequestDto,
                                                    @RequestParam("sortBy") SortingCriteria sortingCriteria,
                                                    @RequestParam("pageNumber") int pageNumber,
                                                    @RequestParam("pageSize") int pageSize)
    {
        return ResponseEntity.ok(searchService.search(query,searchRequestDto.getFilter(),sortingCriteria,pageNumber,pageSize));
    }
}
