package com.msa.service.product;

import com.msa.domain.Product;
import com.msa.domain.repository.ProductRepository;
import com.msa.domain.service.ProductService;
import com.msa.domain.vo.ProductInfo;
import com.msa.infrastructure.kafka.Events;
import com.msa.infrastructure.kafka.producer.ProductEventProducer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@Transactional
public class CreateProduct extends ProductBase{
    @Mock
    ProductRepository productRepository;
    @Mock
    KafkaTemplate<String, Events.ProductCreatedEvent> createdEventTemplate;
    @Mock
    KafkaTemplate<String, Events.ProductUpdatedEvent> updatedEventTemplate;
    @Mock
    ProductEventProducer producer;
    @InjectMocks
    ProductService productService;

    @Test
    @DisplayName("상품 생성 성공")
    void successToCreateProduct() {
        //given
        ProductInfo info = createMockProductInfo("clock", 1000, 100);
        Product product = createMockProduct(info);
        Long fakeProductId = -1L;
        ReflectionTestUtils.setField(product, "id", fakeProductId);
        given(productRepository.save(any())).willReturn(product);

        //when
        Product createdProduct = productService.createProduct(info.getName(),
                info.getPrice().getValue(), info.getCurrentStock().getCount());

        //then
        verify(productRepository).save(any());
        verify(producer).sendCreatedEvent(anyLong(),anyString(),anyInt());
    }

    @Test
    @DisplayName("상품 생성 성공 / 상품 가격은 0원일 수 있다.")
    void successToCreateProductWithZeroPrice() {
        //given
        ProductInfo info = createMockProductInfo("clock", 0, 100);
        Product product = createMockProduct(info);
        Long fakeProductId = -1L;
        ReflectionTestUtils.setField(product, "id", fakeProductId);
        given(productRepository.save(any())).willReturn(product);

        //when
        Product createdProduct = productService.createProduct(info.getName(),
                info.getPrice().getValue(), info.getCurrentStock().getCount());

        //then
        verify(productRepository).save(any());
        verify(producer).sendCreatedEvent(anyLong(),anyString(),anyInt());
    }

    @Test
    @DisplayName("상품 생성 성공 / 상품 재고는 0개일 수 있다.")
    void successToCreateProductWithZeroStock() {
        //given
        ProductInfo info = createMockProductInfo("clock", 1000, 0);
        Product product = createMockProduct(info);
        Long fakeProductId = -1L;
        ReflectionTestUtils.setField(product, "id", fakeProductId);
        given(productRepository.save(any())).willReturn(product);

        //when
        Product createdProduct = productService.createProduct(info.getName(),
                info.getPrice().getValue(), info.getCurrentStock().getCount());

        //then
        verify(productRepository).save(any());
        verify(producer).sendCreatedEvent(anyLong(),anyString(),anyInt());
    }

    @Test
    @DisplayName("상품 생성 실패 / 잘못된 상품 가격")
    void failToCreateProductWithWrongPrice() {
        assertThrows(IllegalArgumentException.class, () -> {
            ProductInfo info = createMockProductInfo("clock", -1, 100);
        });
    }

    @Test
    @DisplayName("상품 생성 실패 / 잘못된 상품 재고")
    void failToCreateProductWithWrongStock() {
        assertThrows(IllegalArgumentException.class, () -> {
            ProductInfo info = createMockProductInfo(" ", 1000, -1);
        });
    }
}
