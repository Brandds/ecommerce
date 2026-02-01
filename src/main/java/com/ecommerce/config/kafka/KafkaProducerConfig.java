package com.ecommerce.config.kafka;

import com.ecommerce.dto.OrderCreatedEvent;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;


@Configuration
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, OrderCreatedEvent> orderProducerFactory(
            KafkaProperties kafkaProperties
    ) {
        var props = kafkaProperties.getProducer().buildProperties();
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, OrderCreatedEvent> orderKafkaTemplate(
            ProducerFactory<String, OrderCreatedEvent> orderProducerFactory
    ) {
        return new KafkaTemplate<>(orderProducerFactory);
    }
}
