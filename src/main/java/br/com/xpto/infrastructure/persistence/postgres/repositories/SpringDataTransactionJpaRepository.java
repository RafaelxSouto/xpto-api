package br.com.xpto.infrastructure.persistence.postgres.repositories;

import br.com.xpto.infrastructure.persistence.postgres.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataTransactionJpaRepository extends JpaRepository<TransactionEntity, String> {
    Optional<TransactionEntity> findByRentalId(String rentalId);
}
