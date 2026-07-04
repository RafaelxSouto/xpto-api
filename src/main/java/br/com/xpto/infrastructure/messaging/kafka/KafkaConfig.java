package br.com.xpto.infrastructure.messaging.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    public static final String RENTAL_EVENTS_TOPIC = "rental-events";

    @Bean
    public NewTopic rentalEventsTopic() {
        return TopicBuilder.name(RENTAL_EVENTS_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
