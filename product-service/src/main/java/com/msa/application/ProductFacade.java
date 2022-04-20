package com.msa.application;

import com.msa.application.dtos.Requests;
import com.msa.application.events.EventUseCase;
import com.msa.domain.Category;
import com.msa.domain.Product;
import com.msa.domain.service.CategoryService;
import com.msa.domain.service.ProductCategoryService;
import com.msa.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductFacade {
    private final EventUseCase eventUseCase;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductCategoryService productCategoryService;

    @Transactional
    public Long createProduct(Requests.CreateProductRequest request) {
        Category category = categoryService.findById(request.getCategoryId());
        Product product = productService.createProduct(request.getName(),request.getPrice(),request.getStock());
        eventUseCase.sendCreatedEvent(product.getId(),product.getProductInfo().getName(),product.getProductInfo().getPrice().getValue());
        productCategoryService.createProductCategory(product, category);

        return product.getId();
    }

    @Transactional
    public void updateProduct(Long productId, Requests.UpdateProductRequest request) {
        Product updatedProduct = productService.updateProduct(productId, request.getName(), request.getPrice(), request.getStock());
        eventUseCase.sendUpdatedEvent(updatedProduct.getId(),updatedProduct.getProductInfo().getName(),updatedProduct.getProductInfo().getPrice().getValue());
    }

    @Transactional
    public Long createCategory(String name) {
        return categoryService.createCategory(name).getId();
    }
}
