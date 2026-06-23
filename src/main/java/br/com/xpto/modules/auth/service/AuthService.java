package br.com.xpto.modules.auth.service;

import br.com.xpto.core.exceptions.BadRequestException;
import br.com.xpto.core.exceptions.InvalidCredentialsException;
import br.com.xpto.core.exceptions.UnauthorizedException;
import br.com.xpto.modules.auth.dto.request.LoginRequest;
import br.com.xpto.modules.auth.dto.request.RegisterRequest;
import br.com.xpto.modules.auth.dto.response.AuthResponse;
import br.com.xpto.modules.auth.entity.UserSession;
import br.com.xpto.modules.auth.repository.UserSessionRepository;
import br.com.xpto.modules.user.entity.User;
import br.com.xpto.modules.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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
  public AuthResponse register(RegisterRequest req, HttpServletRequest httpReq, HttpServletResponse httpRes) {
    if (userRepository.existsByEmail(req.email())) {
      throw new BadRequestException("Email já cadastrado");
    }

    if (userRepository.existsByCpf(req.cpf())) {
      throw new BadRequestException("CPF já cadastrado");
    }

    User user = User.builder()
        .fullName(req.fullName())
        .email(req.email())
        .password(passwordEncoder.encode(req.password()))
        .cpf(req.cpf())
        .phone(req.phone())
        .birthDate(req.birthDate())
        .build();

    userRepository.save(user);
    return createSession(user, httpReq, httpRes);
  }

  @Transactional
  public AuthResponse login(LoginRequest req, HttpServletRequest httpReq, HttpServletResponse httpRes) {
    User user = userRepository.findByEmail(req.email())
        .orElseThrow(InvalidCredentialsException::new);

    if (!passwordEncoder.matches(req.password(), user.getPassword())) {
      throw new InvalidCredentialsException();
    }

    return createSession(user, httpReq, httpRes);
  }

  @Transactional
  public AuthResponse refresh(HttpServletRequest httpReq, HttpServletResponse httpRes) {
    String rawToken = extractRefreshCookie(httpReq);
    String tokenHash = jwtService.hashToken(rawToken);

    UserSession session = sessionRepository.findByRefreshToken(tokenHash)
        .orElseThrow(() -> new UnauthorizedException("Sessão inválida"));

    if (!session.getIsActive() || session.getExpiresAt().isBefore(Instant.now())) {
      throw new UnauthorizedException("Sessão expirada");
    }

    session.setIsActive(true);
    sessionRepository.save(session);

    return toAuthResponse(jwtService.generateAccessToken(session.getUser()), session.getUser());
  }

  @Transactional
  public void logout(HttpServletRequest httpReq, HttpServletResponse httpRes) {
    try {
      String rawToken = extractRefreshCookie(httpReq);
      String tokenHash = jwtService.hashToken(rawToken);
      sessionRepository.findByRefreshToken(tokenHash)
          .ifPresent(s -> {
            s.setIsActive(false);
            sessionRepository.save(s);
          });
    } catch (UnauthorizedException ignored) {
    }

    setRefreshCookie(httpRes, "", Duration.ZERO);
  }

  // ── privados

  private AuthResponse createSession(User user, HttpServletRequest httpReq, HttpServletResponse httpRes) {
    String rawToken = UUID.randomUUID().toString();
    String tokenHash = jwtService.hashToken(rawToken);

    UserSession session = UserSession.builder()
        .user(user)
        .refreshToken(tokenHash)
        .deviceInfo(httpReq.getHeader("User-Agent"))
        .ipAddress(httpReq.getRemoteAddr())
        .expiresAt(Instant.now().plus(SESSION_EXPIRATION_DAYS, ChronoUnit.DAYS))
        .build();

    sessionRepository.save(session);
    setRefreshCookie(httpRes, rawToken, Duration.ofDays(SESSION_EXPIRATION_DAYS));

    return toAuthResponse(jwtService.generateAccessToken(user), user);
  }

  private void setRefreshCookie(HttpServletResponse res, String value, Duration maxAge) {
    ResponseCookie cookie = ResponseCookie.from("refresh_token", value)
        .httpOnly(true)
        .secure(true)
        .sameSite("Strict")
        .path("/auth/refresh")
        .maxAge(maxAge)
        .build();
    res.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
  }

  private String extractRefreshCookie(HttpServletRequest req) {
    if (req.getCookies() == null) {
      throw new UnauthorizedException("Sem refresh token");
    }
    return Arrays.stream(req.getCookies())
        .filter(c -> "refresh_token".equals(c.getName()))
        .findFirst()
        .map(Cookie::getValue)
        .orElseThrow(() -> new UnauthorizedException("Sem refresh token"));
  }

  private AuthResponse toAuthResponse(String accessToken, User user) {
    return new AuthResponse(accessToken, user.getId(), user.getRole().name(), user.getFullName());
  }
}