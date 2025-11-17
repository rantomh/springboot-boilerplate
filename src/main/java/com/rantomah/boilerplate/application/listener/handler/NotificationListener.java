package com.rantomah.boilerplate.application.listener.handler;

import com.rantomah.boilerplate.core.config.kafka.KafkaClient;
import com.rantomah.boilerplate.core.config.kafka.KafkaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class NotificationListener<T> {

    private final KafkaClient<T> kafkaClient;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void handleNotification(KafkaMessage<T> notification) {
        kafkaClient.send("notification-topic", notification);
    }
}
