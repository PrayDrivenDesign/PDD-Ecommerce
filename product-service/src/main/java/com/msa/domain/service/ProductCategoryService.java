package com.msa.domain.service;

import com.msa.domain.Category;
import com.msa.domain.Product;
import com.msa.domain.ProductCategory;
import com.msa.domain.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    public void createProductCategory(Product product, Category category) {
        ProductCategory productCategory = ProductCategory.builder()
                .product(product)
                .category(category)
                .build();

        productCategoryRepository.save(productCategory);
    }
}
