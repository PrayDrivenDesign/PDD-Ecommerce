package com.msa.service.product.eventUseCase;

import com.msa.application.events.EventListener;
import com.msa.domain.Product;
import com.msa.domain.repository.ProductRepository;
import com.msa.domain.service.ProductService;
import com.msa.domain.vo.ProductInfo;
import com.msa.infrastructure.kafka.Events;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class ConsumeOrderCompletedEvent {
    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    EventListener eventListener;


    @Test
    @DisplayName("주문 생성이벤트 consume시 재고 수정을 수행한다")
    void successToEditStock() {
        ProductInfo info = createMockProductInfo("product", 100, 1);
        Product product = Product.builder().productInfo(info).build();
        Product saved = productRepository.save(product);
        Events.OrderCompletedEvent event = new Events.OrderCompletedEvent(saved.getId(), 1);


        //when
        eventListener.consumeOrderCompletedEvent(event);

        //then
        Product targetProduct = productRepository.findById(saved.getId());
        assertTrue(targetProduct.getProductInfo().getCurrentStock().getCount() == 0);
    }



    @Test
    @DisplayName("동시접근시 하나의 재고 수정 요청만 수행된다")
    void successTheOnlyOneRequest() throws InterruptedException {
        //given
        ProductInfo info = createMockProductInfo("product", 100, 1);
        Product product = Product.builder().productInfo(info).build();
        Product saved = productRepository.save(product);
        Events.OrderCompletedEvent event = new Events.OrderCompletedEvent(saved.getId(), 1);

        //생성시 버전은 0
        assertTrue(saved.getVersion() == 0);

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 4; i++) {
            executorService.execute(() -> {
                try {
                    eventListener.consumeOrderCompletedEvent(event);
                } catch (ObjectOptimisticLockingFailureException e) {
                    System.out.println("동시 접근 발생!");
                }
            });
        }

        Thread.sleep(3000);
        Product targetProduct = productRepository.findById(saved.getId());

        //재고가 1개남은 상황에서 동시 수정을 요청한 경우 하나의 요청만 수행되어 재고가0이되며 버전은1로 증가
        assertTrue(targetProduct.getProductInfo().getCurrentStock().getCount() == 0);
        assertTrue(targetProduct.getVersion() ==1);
    }

    @Test
    @DisplayName("동시접근시 하나의 재고 수정 요청만 수행된다")
    void successTheOnlyOneRequest2() throws InterruptedException {
        //given
        ProductInfo info = createMockProductInfo("product", 100, 100);
        Product product = Product.builder().productInfo(info).build();
        Product saved = productRepository.save(product);
        Events.OrderCompletedEvent event = new Events.OrderCompletedEvent(saved.getId(), 1);

        //생성시 버전은 0
        assertTrue(saved.getVersion() == 0);

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 4; i++) {
            executorService.execute(() -> {
                try {
                    eventListener.consumeOrderCompletedEvent(event);
                } catch (ObjectOptimisticLockingFailureException e) {
                    System.out.println("동시 접근 발생!");
                }
            });
        }


        Thread.sleep(3000);
        Product targetProduct = productRepository.findById(saved.getId());

        //재고가 1개남은 상황에서 동시 수정을 요청한 경우 하나의 요청만 수행되어 재고가0이되며 버전은1로 증가
        assertTrue(targetProduct.getProductInfo().getCurrentStock().getCount() == 99);
        assertTrue(targetProduct.getVersion() ==1);
    }



    protected ProductInfo createMockProductInfo(String name, int price, int stock) {
        return ProductInfo.builder()
                .name(name)
                .price(price)
                .currentStock(stock)
                .build();
    }

    protected Product createMockProduct(ProductInfo productInfo) {
        return Product.builder().productInfo(productInfo).build();
    }
}
