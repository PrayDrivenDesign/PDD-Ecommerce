package com.msa.infrastructure.kafka.producer;

import com.msa.infrastructure.kafka.Message;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootStrapServers;

    @Bean
    public ProducerFactory<String, Message.CreateProductRequest> createdEventProducerFactory() {
        Map<String,Object> configs = new HashMap<>();
        configs.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        configs.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory(configs);
    }

    @Bean
    public ProducerFactory<String, Message.UpdateProductRequest> updatedEventProducerFactory() {
        Map<String,Object> configs = new HashMap<>();
        configs.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        configs.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory(configs);
    }

    @Bean
    public KafkaTemplate<String, Message.CreateProductRequest> kafkaTemplateForCreatedEvent() {
        return new KafkaTemplate<>(createdEventProducerFactory());
    }

    @Bean
    public KafkaTemplate<String, Message.UpdateProductRequest> kafkaTemplateForUpdatedEvent() {
        return new KafkaTemplate<>(updatedEventProducerFactory());
    }
}