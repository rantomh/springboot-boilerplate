package com.rantomah.boilerplate.core.config.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaClient<T> {

    private final KafkaTemplate<String, KafkaMessage<T>> kafkaTemplate;

    public KafkaClient(KafkaTemplate<String, KafkaMessage<T>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topic, KafkaMessage<T> event) {
        kafkaTemplate.send(topic, event);
    }
}
