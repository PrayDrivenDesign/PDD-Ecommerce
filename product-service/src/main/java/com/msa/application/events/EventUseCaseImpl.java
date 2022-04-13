package com.msa.application.events;

import com.msa.domain.Product;
import com.msa.domain.service.ProductService;
import com.msa.infrastructure.kafka.Events;
import com.msa.kafka.Topics;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class EventUseCaseImpl implements EventUseCase {
    private final ProductService productService;
    private final KafkaTemplate<String, Events.ProductCreatedEvent> createdEventTemplate;
    private final KafkaTemplate<String, Events.ProductUpdatedEvent> updatedEventTemplate;

    @Override
    public void sendCreatedEvent(Long productId, String productName, int productPrice) {
        Events.ProductCreatedEvent event = new Events.ProductCreatedEvent(productId,productName,productPrice);
        createdEventTemplate.send(Topics.PRODUCT_CREATED_EVENT, event);
    }

    @Override
    public void sendUpdatedEvent(Long productId, String productName, int productPrice) {
        Events.ProductUpdatedEvent event = new Events.ProductUpdatedEvent(productId,productName,productPrice);
        updatedEventTemplate.send(Topics.PRODUCT_UPDATED_EVENT, event);
    }

    @Override
    @Transactional
    @KafkaListener(topics = "orderCompletedEvent", groupId = "product", containerFactory = "orderCompletedEventListener")
    public void consumeOrderCompletedEvent(Events.OrderCompletedEvent event) {
        Product product = productService.findById(event.getProductId());
        product.orderProduct(event.getOrderedProductCount());
    }
}
