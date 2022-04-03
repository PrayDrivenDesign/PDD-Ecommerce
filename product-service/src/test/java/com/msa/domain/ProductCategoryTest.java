package com.msa.domain;

import com.msa.domain.factory.ProductFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryTest {
    @Test
    @DisplayName("카테고리 생성 - 성공")
    void successToCreateCategory() {
        String productName = "clokc";
        int productPrice = 10000;
        int productStock = 100;
        String categoryName = "cate";
        assertDoesNotThrow(() -> {
            ProductFactory productFactory = new ProductFactory();
            Product product = productFactory.createMockProduct(productName, productPrice, productStock);
            Category category = productFactory.createMockCategory(categoryName);
            ProductCategory productCategory = ProductCategory.builder().product(product).category(category).build();
        });
    }
}