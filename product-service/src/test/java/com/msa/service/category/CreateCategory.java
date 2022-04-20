package com.msa.service.category;

import com.msa.domain.Category;
import com.msa.domain.repository.CategoryRepository;
import com.msa.domain.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class CreateCategory extends CategoryBase {
    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    CategoryService categoryService;

    @Test
    @DisplayName("상품 생성 성공")
    void successToCreateCategory() {
        //given
        String categoryName = "foods";

        //when
        assertDoesNotThrow(()->{
            Category createdCategory = categoryService.createCategory(categoryName);
            verify(categoryRepository).save(any());
        });
    }

    @Test
    @DisplayName("상품 생성 실패 - 이미 존재하는 카테고리명")
    void failToCreateCategory() {
        //given
        String categoryName = "foods";
        given(categoryRepository.existsByName(categoryName)).willReturn(true);
        //when
        assertThrows(IllegalArgumentException.class, () -> {
            Category createdCategory = categoryService.createCategory(categoryName);
        });
    }
}
