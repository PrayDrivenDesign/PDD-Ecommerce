package com.msa.domain.repository;

import com.msa.domain.Category;

public interface CategoryRepository {
    Category findById(Long categoryId);

    Category save(Category category);

    boolean existsByName(String name);
}
