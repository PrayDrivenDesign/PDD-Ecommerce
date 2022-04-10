package com.msa.infrastructure.kafka.producer;

import com.msa.infrastructure.kafka.Message;
import com.msa.kafka.Topics;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductEventProducer {
    private final KafkaTemplate<String, Message.CreateProductRequest> createdEventTemplate;
    private final KafkaTemplate<String, Message.UpdateProductRequest> updatedEventTemplate;

    public void sendCreatedEvent(Long productId,String productName,int productPrice) {
        Message.CreateProductRequest message = new Message.CreateProductRequest(productId,productName,productPrice);
        createdEventTemplate.send(Topics.PRODUCT_CREATED_EVENT, message);
    }

    public void sendUpdatedEvent(Long productId,String productName,int productPrice) {
        Message.UpdateProductRequest message = new Message.UpdateProductRequest(productId,productName,productPrice);
        updatedEventTemplate.send(Topics.PRODUCT_UPDATED_EVENT, message);
    }
}
