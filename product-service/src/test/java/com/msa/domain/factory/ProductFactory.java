package com.msa.domain.factory;

import com.msa.domain.Category;
import com.msa.domain.Product;
import com.msa.domain.vo.ProductInfo;

public class ProductFactory {
    public Product createMockProduct(String productName,int productPrice,int productStock) {
        ProductInfo productInfo = ProductInfo.builder()
                .name(productName)
                .price(productPrice)
                .currentStock(productStock)
                .build();

        Product originProduct = Product.builder().productInfo(productInfo).build();
        return originProduct;
    }

    public Category createMockCategory(String categoryName) {
        return Category.builder()
                .categoryName(categoryName)
                .build();
    }
}
