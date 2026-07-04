package br.com.xpto.infrastructure.persistence.postgres.repositories;

import br.com.xpto.core.domain.entities.Rental;
import br.com.xpto.core.domain.repositories.RentalRepository;
import br.com.xpto.infrastructure.persistence.postgres.entities.RentalEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RentalRepositoryImpl implements RentalRepository {

    private final SpringDataRentalJpaRepository repository;

    @Override
    public Rental save(Rental rental) {
        RentalEntity entity = RentalEntity.fromDomain(rental);
        RentalEntity savedEntity = repository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public Optional<Rental> findById(String id) {
        return repository.findById(id).map(RentalEntity::toDomain);
    }

    @Override
    public List<Rental> findByRenterId(String renterId) {
        return repository.findByRenterId(renterId).stream()
                .map(RentalEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Rental> findByOwnerId(String ownerId) {
        return repository.findByOwnerId(ownerId).stream()
                .map(RentalEntity::toDomain)
                .collect(Collectors.toList());
    }
}
