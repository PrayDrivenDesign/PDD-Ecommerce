package com.msa.domain.service;

import com.msa.domain.Product;
import com.msa.domain.repository.ProductRepository;
import com.msa.domain.vo.ProductInfo;
import com.msa.infrastructure.kafka.producer.ProductEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductEventProducer producer;

    @Transactional
    public Product createProduct(String name, int price, int stock) {
        ProductInfo productInfo = ProductInfo.builder()
                .name(name)
                .price(price)
                .currentStock(stock)
                .build();
        Product product = Product.builder().productInfo(productInfo).build();
        Product saved = productRepository.save(product);

        // produce
        producer.sendCreatedEvent(saved.getId(),
                saved.getProductInfo().getName(),saved.getProductInfo().getPrice().getValue());

        return saved;
    }

    @Transactional(readOnly = true)
    public Product findById(Long productId) {
        return productRepository.findById(productId);
    }

    @Transactional
    public void updateProduct(Long productId, String name, Integer price, Integer stock) {
        Product originProduct = productRepository.findById(productId);
        originProduct.editProductNameInfo(name);
        originProduct.editProductPriceInfo(price);
        originProduct.editProductStockInfo(stock);

        // produce
        producer.sendUpdatedEvent(originProduct.getId(),
                originProduct.getProductInfo().getName(), originProduct.getProductInfo().getPrice().getValue());
    }
}
