package com.msa.application;

import com.msa.application.dtos.Requests;
import com.msa.domain.Category;
import com.msa.domain.Product;
import com.msa.domain.service.CategoryService;
import com.msa.domain.service.ProductCategoryService;
import com.msa.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ProductFacade {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductCategoryService productCategoryService;

    public Long createProduct(Requests.CreateProductRequest request) {
        Category category = categoryService.findById(request.getCategoryId());
        Product product = productService.createProduct(request.getName(),request.getPrice(),request.getStock());
        productCategoryService.createProductCategory(product,category);
        return product.getId();
    }
}
