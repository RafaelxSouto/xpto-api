package br.com.xpto.infrastructure.persistence.postgres.repositories;

import br.com.xpto.infrastructure.persistence.postgres.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataUserJpaRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
