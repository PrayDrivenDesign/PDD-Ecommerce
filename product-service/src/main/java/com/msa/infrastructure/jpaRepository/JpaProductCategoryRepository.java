package com.msa.infrastructure.jpaRepository;

import com.msa.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductCategoryRepository extends JpaRepository<ProductCategory,Long> {
}
