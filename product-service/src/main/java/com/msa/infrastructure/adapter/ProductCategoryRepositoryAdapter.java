package com.msa.infrastructure.adapter;

import com.msa.domain.ProductCategory;
import com.msa.domain.repository.ProductCategoryRepository;
import com.msa.infrastructure.jpaRepository.JpaProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductCategoryRepositoryAdapter implements ProductCategoryRepository {
    private final JpaProductCategoryRepository jpaProductCategoryRepository;


    @Override
    public void save(ProductCategory productCategory) {
        jpaProductCategoryRepository.save(productCategory);
    }
}
