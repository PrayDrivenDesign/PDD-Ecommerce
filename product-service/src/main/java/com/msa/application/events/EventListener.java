package com.msa.application.events;

import com.msa.domain.service.ProductService;
import com.msa.infrastructure.kafka.Events;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventListener {
    private final ProductService productService;

    @KafkaListener(topics = "orderCompletedEvent", groupId = "product", containerFactory = "orderCompletedEventListener")
    public void consumeOrderCompletedEvent(Events.OrderCompletedEvent event) {
        try {
            productService.editProductStock(event);
        } catch (ObjectOptimisticLockingFailureException e) {
            log.error("동시 접근으로인한 재고 수정 실패");
        }
    }
}
