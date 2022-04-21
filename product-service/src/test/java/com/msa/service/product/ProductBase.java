package com.msa.service.product;

import com.msa.application.dtos.Requests;
import com.msa.domain.Category;
import com.msa.domain.Product;
import com.msa.domain.ProductCategory;
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

    protected Category createMockCategory(String categoryName) {
        return new Category(categoryName);
    }

    protected ProductCategory createMockProductCategory(Product product,Category category) {
        return new ProductCategory(product, category);
    }

    protected Requests.CreateProductRequest createMockCreateRequest() {
        return new Requests.CreateProductRequest("product", 100, 100, 1L);
    }

    protected Requests.UpdateProductRequest createMockUpdateRequest() {
        return new Requests.UpdateProductRequest("updated", 101, 101);
    }
}
