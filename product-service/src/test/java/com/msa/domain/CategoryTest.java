package com.msa.domain;

import com.msa.domain.factory.ProductFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
    @Test
    @DisplayName("카테고리 생성 - 성공")
    void successToCreateCategory() {
        String targetName = "cate";
        assertDoesNotThrow(()->{
            Category category = new ProductFactory().createMockCategory(targetName);
            assertTrue(category.getName().equals(targetName));
        });
    }
}