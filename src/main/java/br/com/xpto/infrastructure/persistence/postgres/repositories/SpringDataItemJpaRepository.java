package br.com.xpto.infrastructure.persistence.postgres.repositories;

import br.com.xpto.infrastructure.persistence.postgres.entities.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataItemJpaRepository extends JpaRepository<ItemEntity, String> {
    List<ItemEntity> findByAvailableTrue();
    List<ItemEntity> findByOwnerId(String ownerId);
}
