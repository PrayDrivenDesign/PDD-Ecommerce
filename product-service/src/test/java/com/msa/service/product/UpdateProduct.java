package com.msa.service.product;

import com.msa.domain.Product;
import com.msa.domain.repository.ProductRepository;
import com.msa.domain.service.ProductService;
import com.msa.domain.vo.ProductInfo;
import com.msa.infrastructure.kafka.Message;
import com.msa.infrastructure.kafka.producer.ProductEventProducer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@Transactional
public class UpdateProduct extends ProductBase{
    @Spy
    ProductRepository productRepository;
    @Mock
    KafkaTemplate<String, Message.CreateProductRequest> createdEventTemplate;
    @Mock
    KafkaTemplate<String, Message.UpdateProductRequest> updatedEventTemplate;
    @Mock
    ProductEventProducer producer;
    @InjectMocks
    ProductService productService;

    @Test
    @DisplayName("상품 수정 성공")
    void successToUpdateProduct() {
        //given
        ProductInfo info = createMockProductInfo("originProductName", 100, 100);
        Product product = createMockProduct(info);
        given(productRepository.findById(any())).willReturn(product);
        //when
        productService.updateProduct(product.getId(), "newName", 1, 1);

        //then
        assertTrue(product.getProductInfo().getName().equals("newName"));
        assertTrue(product.getProductInfo().getCurrentStock().getCount() == 1);
        assertTrue(product.getProductInfo().getPrice().getValue() == 1);

    }

    @Test
    @DisplayName("상품 수정 실패 / 가격이 음수인 경우")
    void failToUpdateProductWithPrice() {
        //given
        ProductInfo info = createMockProductInfo("originProductName", 100, 100);
        Product product = createMockProduct(info);
        given(productRepository.findById(any())).willReturn(product);
        //when
        assertThrows(IllegalArgumentException.class, () -> {
            productService.updateProduct(product.getId(), "newName", -1, 1);
        });

    }

    @Test
    @DisplayName("상품 수정 실패 / 재고가 음수인 경우")
    void failToUpdateProductWithStock() {
        //given
        ProductInfo info = createMockProductInfo("originProductName", 100, 100);
        Product product = createMockProduct(info);
        given(productRepository.findById(any())).willReturn(product);
        //when
        assertThrows(IllegalArgumentException.class, () -> {
            productService.updateProduct(product.getId(), "newName", 1, -1);
        });
    }
}
