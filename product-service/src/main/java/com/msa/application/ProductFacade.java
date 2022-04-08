package com.msa.application;

import com.msa.application.dtos.Requests;
import com.msa.domain.Category;
import com.msa.domain.Product;
import com.msa.domain.service.CategoryService;
import com.msa.domain.service.ProductCategoryService;
import com.msa.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ProductFacade {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductCategoryService productCategoryService;

    @Transactional
    public Long createProduct(Requests.CreateProductRequest request) {
        Category category = categoryService.findById(request.getCategoryId());
        Product product = productService.createProduct(request.getName(),request.getPrice(),request.getStock());
        productCategoryService.createProductCategory(product, category);
        //todo send event to order aggregate
        return product.getId();
    }

    @Transactional
    public void updateProduct(Long productId, Requests.UpdateProductRequest request) {
        Product originProduct = productService.findById(productId);
        productService.updateProduct(originProduct, request.getName(), request.getPrice(), request.getStock());
        //todo send event to order aggregate
    }
}