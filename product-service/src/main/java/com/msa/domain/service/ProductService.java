package com.msa.domain.service;

import com.msa.domain.Product;
import com.msa.domain.repository.ProductRepository;
import com.msa.domain.vo.ProductInfo;
import com.msa.infrastructure.kafka.Events;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
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
        Product saved = productRepository.save(product);

        return saved;
    }

    @Transactional(readOnly = true)
    public Product findById(Long productId) {
        return productRepository.findById(productId);
    }

    @Transactional
    public Product updateProduct(Long productId, String name, Integer price, Integer stock) {
        Product originProduct = productRepository.findById(productId);
        originProduct.editProductInfo(name, price, stock);

        return originProduct;
    }

    @Transactional
    public void editProductStock(Events.OrderCompletedEvent event) throws ObjectOptimisticLockingFailureException {
        Product product = productRepository.findById(event.getProductId());
        product.orderProduct(event.getOrderedProductCount());
    }

    @Transactional
    public void save(Product product) {
        productRepository.save(product);
    }
}
