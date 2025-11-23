package com.rantomah.boilerplate.infrastructure.broker.notification;

import com.rantomah.boilerplate.application.domain.dto.NotificationDTO;
import com.rantomah.boilerplate.core.config.kafka.KafkaMessage;
import java.time.Instant;
import java.util.Map;
import lombok.Data;
import lombok.Getter;

@Data
public class NotificationEvent implements KafkaMessage<NotificationDTO> {

    private NotificationDTO notification;

    @Getter
    public static class Builder {

        private String type;
        private String template;
        private String to;
        private Map<String, Object> data;

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder template(String template) {
            this.template = template;
            return this;
        }

        public Builder to(String to) {
            this.to = to;
            return this;
        }

        public Builder data(Map<String, Object> data) {
            this.data = data;
            return this;
        }

        public NotificationEvent build() {
            return new NotificationEvent(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public NotificationEvent(Builder builder) {
        this.notification =
                NotificationDTO.builder()
                        .type(builder.getType())
                        .to(builder.getTo())
                        .template(builder.getTemplate())
                        .data(builder.getData())
                        .build();
    }

    private final Instant datetime = Instant.now();

    @Override
    public NotificationDTO getContent() {
        return notification;
    }
}
