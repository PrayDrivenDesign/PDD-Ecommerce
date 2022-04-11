package com.msa.presentation.factory;

import com.msa.application.dtos.Requests;
import com.msa.domain.Category;
import com.msa.domain.Product;
import com.msa.domain.repository.CategoryRepository;
import com.msa.domain.repository.ProductCategoryRepository;
import com.msa.domain.repository.ProductRepository;

import com.msa.domain.vo.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductFactory {
    @Autowired
    public ProductRepository productRepository;
    @Autowired
    public CategoryRepository categoryRepository;
    @Autowired
    public ProductCategoryRepository productCategoryRepository;


    public Category createMockCategory() {
        Category category = Category.builder()
                .categoryName("tempCategory")
                .build();

        return categoryRepository.save(category);
    }

    public Product createMockProduct() {
        ProductInfo info = ProductInfo.builder()
                .name("origin")
                .currentStock(0)
                .price(0)
                .build();
        Product product = Product.builder()
                .productInfo(info)
                .build();
        return productRepository.save(product);
    }

    public Requests.CreateProductRequest createProductRequest(String name, Integer price, Integer stock, Long categoryId) {
        return new Requests.CreateProductRequest(name, price, stock, categoryId);
    }

    public Requests.UpdateProductRequest updateProductRequest(String name, Integer price, Integer stock) {
        return new Requests.UpdateProductRequest(name, price, stock);
    }

    public Requests.CreateCategoryRequest createCategoryRequest(String name) {
        return new Requests.CreateCategoryRequest(name);
    }
}
