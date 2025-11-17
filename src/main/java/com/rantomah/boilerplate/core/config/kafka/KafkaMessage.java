package com.rantomah.boilerplate.core.config.kafka;

public interface KafkaMessage<T> {

    T getContent();
}
