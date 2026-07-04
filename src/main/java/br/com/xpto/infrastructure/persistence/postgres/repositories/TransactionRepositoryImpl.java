package br.com.xpto.infrastructure.persistence.postgres.repositories;

import br.com.xpto.core.domain.entities.Transaction;
import br.com.xpto.core.domain.repositories.TransactionRepository;
import br.com.xpto.infrastructure.persistence.postgres.entities.TransactionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {

    private final SpringDataTransactionJpaRepository repository;

    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity entity = TransactionEntity.fromDomain(transaction);
        TransactionEntity savedEntity = repository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public Optional<Transaction> findById(String id) {
        return repository.findById(id).map(TransactionEntity::toDomain);
    }

    @Override
    public Optional<Transaction> findByRentalId(String rentalId) {
        return repository.findByRentalId(rentalId).map(TransactionEntity::toDomain);
    }
}
