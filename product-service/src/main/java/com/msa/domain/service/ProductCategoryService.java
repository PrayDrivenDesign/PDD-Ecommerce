package com.msa.domain.service;

import com.msa.domain.Category;
import com.msa.domain.Product;
import com.msa.domain.ProductCategory;
import com.msa.domain.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    @Transactional
    public ProductCategory createProductCategory(Product product, Category category) {
        ProductCategory productCategory = ProductCategory.builder()
                .product(product)
                .category(category)
                .build();

        return productCategoryRepository.save(productCategory);
    }
}
