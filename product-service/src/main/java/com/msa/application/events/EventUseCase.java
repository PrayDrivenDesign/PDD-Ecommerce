package com.msa.application.events;

import com.msa.infrastructure.kafka.Events;

public interface EventUseCase {
    public void sendCreatedEvent(Long productId, String productName, int productPrice);

    public void sendUpdatedEvent(Long productId, String productName, int productPrice);

    public void consumeOrderCompletedEvent(Events.OrderCompletedEvent event);
}
