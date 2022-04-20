package com.msa.service.category;


import com.msa.domain.Category;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryBase {
    protected Category createMockCategory(String categoryName) {
        return Category.builder().categoryName(categoryName).build();
    }
}
