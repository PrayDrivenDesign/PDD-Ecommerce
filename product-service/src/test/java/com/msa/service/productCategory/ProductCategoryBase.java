package com.msa.service.productCategory;

import com.msa.domain.Category;
import com.msa.domain.Product;
import com.msa.domain.vo.ProductInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductCategoryBase {
    protected ProductInfo createMockProductInfo(String name, int price, int stock) {
        return ProductInfo.builder()
                .name(name)
                .price(price)
                .currentStock(stock)
                .build();
    }

    protected Product createMockProduct(ProductInfo productInfo) {
        return Product.builder().productInfo(productInfo).build();
    }

    protected Category createMockCategory(String categoryName) {
        return Category.builder().categoryName(categoryName).build();
    }
}
