package br.com.xpto.modules.auth.repository;

import br.com.xpto.modules.auth.entity.UserSession;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSessionRepository extends JpaRepository<UserSession, UUID> {

  Optional<UserSession> findByRefreshToken(String refreshToken);

  List<UserSession> findByUserId(UUID userId);

}
