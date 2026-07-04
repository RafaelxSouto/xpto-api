package br.com.xpto.core.domain.repositories;

import br.com.xpto.core.domain.entities.Rental;
import java.util.List;
import java.util.Optional;

public interface RentalRepository {
    Rental save(Rental rental);
    Optional<Rental> findById(String id);
    List<Rental> findByRenterId(String renterId);
    List<Rental> findByOwnerId(String ownerId);
}
