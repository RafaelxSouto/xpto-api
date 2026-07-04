package br.com.xpto.infrastructure.persistence.postgres.repositories;

import br.com.xpto.core.domain.entities.User;
import br.com.xpto.core.domain.repositories.UserRepository;
import br.com.xpto.infrastructure.persistence.postgres.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final SpringDataUserJpaRepository jpaRepository;

    @Override
    public User save(User user) {
        UserEntity entity = UserEntity.fromDomain(user);
        UserEntity saved = jpaRepository.save(entity);
        return saved.toDomain();
    }

    @Override
    public Optional<User> findById(String id) {
        return jpaRepository.findById(id).map(UserEntity::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(UserEntity::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
}
