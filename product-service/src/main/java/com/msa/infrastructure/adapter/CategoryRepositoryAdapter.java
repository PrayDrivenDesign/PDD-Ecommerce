package com.msa.infrastructure.adapter;

import com.msa.domain.Category;
import com.msa.domain.repository.CategoryRepository;
import com.msa.infrastructure.jpaRepository.JpaCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CategoryRepositoryAdapter implements CategoryRepository {
    private final JpaCategoryRepository jpaCategoryRepository;

    @Override
    public Category findById(Long categoryId) {
        return jpaCategoryRepository.findById(categoryId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 카테고리입니다."));
    }

    @Override
    public Category save(Category category) {
        return jpaCategoryRepository.save(category);
    }

    @Override
    public boolean existsByName(String name) {
        return jpaCategoryRepository.existsByName(name);
    }
}
