package com.msa.domain.service;

import com.msa.common.ErrorMessages;
import com.msa.domain.Category;
import com.msa.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Transactional
    public Category createCategory(String categoryName) {
        if (categoryRepository.existsByName(categoryName)) {
            throw new IllegalArgumentException(ErrorMessages.ALREADY_EXISTED_CATEGORY_EXCEPTION);
        }
        Category category = Category.builder().categoryName(categoryName).build();
        return categoryRepository.save(category);
    }
}
