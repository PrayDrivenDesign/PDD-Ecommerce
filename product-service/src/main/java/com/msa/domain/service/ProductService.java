package com.msa.domain.service;

import com.msa.domain.Product;
import com.msa.domain.repository.ProductRepository;
import com.msa.domain.vo.Money;
import com.msa.domain.vo.ProductInfo;
import com.msa.domain.vo.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

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
