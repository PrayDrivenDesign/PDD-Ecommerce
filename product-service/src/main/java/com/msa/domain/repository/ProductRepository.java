package com.msa.domain.repository;

import com.msa.domain.Product;

public interface ProductRepository {
    Product save(Product product);

    Product findById(Long productId);
}
