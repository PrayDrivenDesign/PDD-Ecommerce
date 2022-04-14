package com.msa.application.events;


public interface EventUseCase {
    public void sendCreatedEvent(Long productId, String productName, int productPrice);

    public void sendUpdatedEvent(Long productId, String productName, int productPrice);
}
