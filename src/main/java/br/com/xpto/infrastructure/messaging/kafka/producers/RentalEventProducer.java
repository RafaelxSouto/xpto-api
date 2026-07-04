package br.com.xpto.infrastructure.messaging.kafka.producers;

import br.com.xpto.core.domain.entities.Rental;
import br.com.xpto.infrastructure.messaging.kafka.KafkaConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RentalEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishRentalCreatedEvent(Rental rental) {
        log.info("Publishing RENTAL_CREATED event for rental ID: {}", rental.getId());
        kafkaTemplate.send(KafkaConfig.RENTAL_EVENTS_TOPIC, "RENTAL_CREATED", rental);
    }

    public void publishRentalAcceptedEvent(Rental rental) {
        log.info("Publishing RENTAL_ACCEPTED event for rental ID: {}", rental.getId());
        kafkaTemplate.send(KafkaConfig.RENTAL_EVENTS_TOPIC, "RENTAL_ACCEPTED", rental);
    }

    public void publishRentalDisputedEvent(Rental rental) {
        log.info("Publishing RENTAL_DISPUTED event for rental ID: {}", rental.getId());
        kafkaTemplate.send(KafkaConfig.RENTAL_EVENTS_TOPIC, "RENTAL_DISPUTED", rental);
    }
}
