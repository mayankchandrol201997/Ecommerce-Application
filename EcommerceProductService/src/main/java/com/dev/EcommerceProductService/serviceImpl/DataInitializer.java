package com.dev.EcommerceProductService.serviceImpl;

import com.dev.EcommerceProductService.model.Category;
import com.dev.EcommerceProductService.model.Product;
import com.dev.EcommerceProductService.repository.CategoryRepository;
import com.dev.EcommerceProductService.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) {

        if (productRepository.count() > 0) {
            return;
        }

        Category category = categoryRepository.findByCategoryName("Electronics")
                .orElseGet(() -> {
                    Category c = new Category();
                    c.setCategoryName("Electronics");
                    return categoryRepository.save(c);
                });

        List<Product> products = List.of(
                createProduct("iPhone 15", "Apple smartphone", "img1.jpg", category, 999.99),
                createProduct("MacBook Pro", "Apple laptop", "img2.jpg", category, 2499.00),
                createProduct("AirPods Pro", "Wireless earbuds", "img3.jpg", category, 249.99),
                createProduct("iPad Pro", "Apple tablet", "img4.jpg", category, 1099.00),
                createProduct("Apple Watch", "Smart watch", "img5.jpg", category, 399.99),
                createProduct("Samsung Galaxy S24", "Android phone", "img6.jpg", category, 899.99),
                createProduct("Dell XPS 15", "Windows laptop", "img7.jpg", category, 2199.00),
                createProduct("Sony WH-1000XM5", "Noise canceling headphones", "img8.jpg", category, 349.99),
                createProduct("Canon EOS R5", "Mirrorless camera", "img9.jpg", category, 3899.00),
                createProduct("PlayStation 5", "Gaming console", "img10.jpg", category, 499.99)
        );

        productRepository.saveAll(products);
    }

    private Product createProduct(
            String title,
            String description,
            String image,
            Category category,
            double price
    ) {
        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setImage(image);
        product.setCategory(category);
        product.setPrice(price);
        return product;
    }
}
