package br.com.xpto.core.application.usecases;

import br.com.xpto.core.application.dto.RentalRequestDTO;
import br.com.xpto.core.application.dto.RentalResponseDTO;
import br.com.xpto.core.domain.entities.Item;
import br.com.xpto.core.domain.entities.Rental;
import br.com.xpto.core.domain.repositories.ItemRepository;
import br.com.xpto.core.domain.repositories.RentalRepository;
import br.com.xpto.infrastructure.messaging.kafka.producers.RentalEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalUseCase {

    private final RentalRepository rentalRepository;
    private final ItemRepository itemRepository;
    private final RentalEventProducer eventProducer;

    public RentalResponseDTO requestRental(RentalRequestDTO request) {
        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        if (!item.isAvailable()) {
            throw new IllegalStateException("Item is not available for rent");
        }

        Rental rental = Rental.createRequest(
                item.getId(),
                request.getRenterId(),
                item.getOwnerId(),
                request.getStartDate(),
                request.getEndDate(),
                request.getExpectedTotalAmount()
        );

        Rental savedRental = rentalRepository.save(rental);
        eventProducer.publishRentalCreatedEvent(savedRental);

        return RentalResponseDTO.fromDomain(savedRental);
    }

    public RentalResponseDTO acceptRental(String rentalId, String ownerId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new IllegalArgumentException("Rental not found"));

        if (!rental.getOwnerId().equals(ownerId)) {
            throw new IllegalStateException("Only the owner can accept this rental");
        }

        rental.accept();
        Rental savedRental = rentalRepository.save(rental);
        
        eventProducer.publishRentalAcceptedEvent(savedRental);

        return RentalResponseDTO.fromDomain(savedRental);
    }

    public RentalResponseDTO openDispute(String rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new IllegalArgumentException("Rental not found"));

        rental.openDispute();
        Rental savedRental = rentalRepository.save(rental);

        eventProducer.publishRentalDisputedEvent(savedRental);

        return RentalResponseDTO.fromDomain(savedRental);
    }

    public List<RentalResponseDTO> getRentalsByRenter(String renterId) {
        return rentalRepository.findByRenterId(renterId).stream()
                .map(RentalResponseDTO::fromDomain)
                .collect(Collectors.toList());
    }

    public List<RentalResponseDTO> getRentalsByOwner(String ownerId) {
        return rentalRepository.findByOwnerId(ownerId).stream()
                .map(RentalResponseDTO::fromDomain)
                .collect(Collectors.toList());
    }
}
