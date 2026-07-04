package br.com.xpto.infrastructure.persistence.postgres.repositories;

import br.com.xpto.infrastructure.persistence.postgres.entities.RentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataRentalJpaRepository extends JpaRepository<RentalEntity, String> {
    List<RentalEntity> findByRenterId(String renterId);
    List<RentalEntity> findByOwnerId(String ownerId);
}
