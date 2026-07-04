package br.com.xpto.core.domain.repositories;

import br.com.xpto.core.domain.entities.Transaction;
import java.util.Optional;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
    Optional<Transaction> findById(String id);
    Optional<Transaction> findByRentalId(String rentalId);
}
