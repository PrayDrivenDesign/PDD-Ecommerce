package com.msa.infrastructure.kafka.consumer;

import com.msa.infrastructure.kafka.Events;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootStrapServers;

    @Bean
    public ConsumerFactory<String, Events.OrderCompletedEvent> orderCompletedEventConsumerFactory() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        configs.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, "product");

        return new DefaultKafkaConsumerFactory<>(
                configs,
                new StringDeserializer(),
                new JsonDeserializer<>(Events.OrderCompletedEvent.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Events.OrderCompletedEvent> orderCompletedEventListener() {
        ConcurrentKafkaListenerContainerFactory<String, Events.OrderCompletedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(orderCompletedEventConsumerFactory());
        return factory;
    }

}
