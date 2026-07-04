package br.com.xpto.core.domain.repositories;

import br.com.xpto.core.domain.entities.User;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
