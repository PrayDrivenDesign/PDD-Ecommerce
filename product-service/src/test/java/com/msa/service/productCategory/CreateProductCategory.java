package com.msa.service.productCategory;


import com.msa.domain.Category;
import com.msa.domain.Product;
import com.msa.domain.ProductCategory;
import com.msa.domain.repository.ProductCategoryRepository;
import com.msa.domain.service.ProductCategoryService;
import com.msa.domain.vo.ProductInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


public class CreateProductCategory extends ProductCategoryBase {
    @Mock
    ProductCategoryRepository productCategoryRepository;

    @InjectMocks
    ProductCategoryService productCategoryService;

    @Test
    @DisplayName("productCategory 생성 성공")
    void successToCreateProductCategory() {
        //given
        ProductInfo info = createMockProductInfo("clock", 100, 100);
        Product product = createMockProduct(info);
        Long fakeProductId = -1L;
        ReflectionTestUtils.setField(product, "id", fakeProductId);

        Category category = createMockCategory("category");
        Long fakeCategoryId = -1L;
        ReflectionTestUtils.setField(category, "id", fakeCategoryId);

        ProductCategory productCategory = ProductCategory.builder().product(product).category(category).build();
        Long fakeId = -1L;
        ReflectionTestUtils.setField(productCategory, "id", fakeId);

        //when
        ProductCategory result = productCategoryService.createProductCategory(product, category);

        //then
        verify(productCategoryRepository).save(any());
    }
}
