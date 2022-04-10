package com.msa.infrastructure.adapter;

import com.msa.domain.Product;
import com.msa.domain.repository.ProductRepository;
import com.msa.infrastructure.jpaRepository.JpaProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {
    private final JpaProductRepository jpaProductRepository;

    @Override
    public Product save(Product product) {
        return jpaProductRepository.save(product);
    }

    @Override
    public Product findById(Long productId) {
        return jpaProductRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
    }
}
