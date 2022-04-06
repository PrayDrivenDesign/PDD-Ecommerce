package com.msa.domain.repository;

import com.msa.domain.ProductCategory;

public interface ProductCategoryRepository {
    ProductCategory save(ProductCategory productCategory);

    ProductCategory findById(Long id);
}
