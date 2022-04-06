package com.msa.domain.service;

import com.msa.domain.Product;
import com.msa.domain.repository.ProductRepository;
import com.msa.domain.vo.ProductInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public Product createProduct(String name, int price, int stock) {
        ProductInfo productInfo = ProductInfo.builder()
                .name(name)
                .price(price)
                .currentStock(stock)
                .build();
        Product product = Product.builder().productInfo(productInfo).build();
        return productRepository.save(product);
    }
}
