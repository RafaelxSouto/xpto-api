package br.com.xpto.infrastructure.messaging.kafka.consumers;

import br.com.xpto.core.domain.entities.Rental;
import br.com.xpto.infrastructure.messaging.kafka.KafkaConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RentalEventConsumer {

    @KafkaListener(topics = KafkaConfig.RENTAL_EVENTS_TOPIC, groupId = "email-notification-group")
    public void handleRentalEvents(Rental rental) {
        log.info("Received Rental Event for ID: {}. Current Status: {}", rental.getId(), rental.getStatus());
        
        switch (rental.getStatus()) {
            case PENDING:
                log.info("📧 SIMULATING EMAIL: Sending 'New Rental Request' email to Owner ID: {}", rental.getOwnerId());
                break;
            case ACCEPTED:
                log.info("📧 SIMULATING EMAIL: Sending 'Rental Accepted' email to Renter ID: {}. Proceeding to Stripe payment.", rental.getRenterId());
                break;
            case DISPUTED:
                log.info("📧 SIMULATING EMAIL: ⚠️ URGENT: Sending 'Dispute Opened' email to Renter and Owner for Rental ID: {}", rental.getId());
                break;
            default:
                log.info("Received event for status: {} but no email logic is configured.", rental.getStatus());
        }
    }
}
