package br.com.xpto.modules.auth.service;

import br.com.xpto.core.exceptions.BadRequestException;
import br.com.xpto.core.exceptions.InvalidCredentialsException;
import br.com.xpto.core.exceptions.UnauthorizedException;
import br.com.xpto.modules.auth.dto.request.LoginRequest;
import br.com.xpto.modules.auth.dto.request.RegisterRequest;
import br.com.xpto.modules.auth.dto.response.AuthResponse;
import br.com.xpto.modules.auth.dto.response.AuthResult;
import br.com.xpto.modules.auth.entity.UserSession;
import br.com.xpto.modules.auth.repository.UserSessionRepository;
import br.com.xpto.modules.user.entity.User;
import br.com.xpto.modules.user.repository.UserRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

  private static final int SESSION_EXPIRATION_DAYS = 30;

  private final UserRepository userRepository;
  private final UserSessionRepository sessionRepository;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public User register(RegisterRequest req) {
    if (userRepository.existsByEmail(req.email())) {
      throw new BadRequestException("Email já cadastrado");
    }

    if (userRepository.existsByCpf(req.cpf())) {
      throw new BadRequestException("CPF já cadastrado");
    }

    if (userRepository.existsByUserName(req.userName())) {
      throw new BadRequestException("Nome de usuário já cadastrado");
    }

    User user = User.builder().fullName(req.fullName()).userName(req.userName()).email(req.email())
        .password(passwordEncoder.encode(req.password())).cpf(req.cpf()).phone(req.phone())
        .birthDate(req.birthDate()).build();

    return userRepository.save(user);
  }

  @Transactional
  public AuthResult login(LoginRequest req) {
    User user = userRepository.findByEmail(req.email())
        .orElseThrow(InvalidCredentialsException::new);

    if (!passwordEncoder.matches(req.password(), user.getPassword())) {
      throw new InvalidCredentialsException();
    }

    return createSession(user);
  }

  @Transactional
  public AuthResult refresh(String refreshToken) {
    if (refreshToken == null) {
      throw new UnauthorizedException("Sem refresh token");
    }

    String tokenHash = jwtService.hashToken(refreshToken);

    UserSession session = sessionRepository.findByRefreshToken(tokenHash)
        .orElseThrow(() -> new UnauthorizedException("Sessão inválida"));

    if (!session.getIsActive() || session.getExpiresAt().isBefore(Instant.now())) {
      throw new UnauthorizedException("Sessão expirada");
    }

    session.setIsActive(false);
    sessionRepository.save(session);

    return createSession(session.getUser());
  }

  @Transactional
  public void logout(String refreshToken) {
    if (refreshToken == null) {
      return;
    }

    String tokenHash = jwtService.hashToken(refreshToken);
    sessionRepository.findByRefreshToken(tokenHash).ifPresent(s -> {
      s.setIsActive(false);
      sessionRepository.save(s);
    });
  }

  // ── private

  private AuthResult createSession(User user) {
    String rawToken = UUID.randomUUID().toString();
    String tokenHash = jwtService.hashToken(rawToken);

    UserSession session = UserSession.builder().user(user).refreshToken(tokenHash)
        .expiresAt(Instant.now().plus(SESSION_EXPIRATION_DAYS, ChronoUnit.DAYS)).build();

    sessionRepository.save(session);

    AuthResponse authResponse = toAuthResponse(jwtService.generateAccessToken(user), user);
    return new AuthResult(authResponse, rawToken);
  }

  private AuthResponse toAuthResponse(String accessToken, User user) {
    return new AuthResponse(accessToken, user.getId(), user.getRole().name(), user.getFullName());
  }
}
