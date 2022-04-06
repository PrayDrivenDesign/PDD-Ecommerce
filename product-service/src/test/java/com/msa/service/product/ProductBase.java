package com.msa.service.product;

import com.msa.domain.Product;
import com.msa.domain.vo.ProductInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductBase {
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
}
