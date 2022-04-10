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
    public ProductCategory save(ProductCategory productCategory) {
        return jpaProductCategoryRepository.save(productCategory);
    }

    @Override
    public ProductCategory findById(Long id) {
        return jpaProductCategoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 productCategory가 존재하지 않습니다."));
    }
}
