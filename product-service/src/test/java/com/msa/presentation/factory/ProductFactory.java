package com.msa.presentation.factory;

import com.msa.application.request.CreateProductRequest;
import com.msa.domain.Category;
import com.msa.domain.repository.CategoryRepository;
import com.msa.domain.repository.ProductCategoryRepository;
import com.msa.domain.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductFactory {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductCategoryRepository productCategoryRepository;


    public Category createMockCategory() {
        Category category = Category.builder()
                .categoryName("tempCategory")
                .build();

        return categoryRepository.save(category);
    }

    public CreateProductRequest createProductRequest(String name, Integer price, Integer stock, Long categoryId) {
        return new CreateProductRequest(name, price, stock, categoryId);
    }
}
