package br.com.xpto.modules.auth.controller;

import br.com.xpto.core.utils.CookieUtils;
import br.com.xpto.modules.auth.dto.request.LoginRequest;
import br.com.xpto.modules.auth.dto.request.RegisterRequest;
import br.com.xpto.modules.auth.dto.response.AuthResponse;
import br.com.xpto.modules.auth.dto.response.AuthResult;
import br.com.xpto.modules.auth.service.AuthService;
import br.com.xpto.modules.auth.dto.response.RegisterResponse;
import br.com.xpto.modules.user.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<RegisterResponse> register(
      @RequestBody
      @Valid
      RegisterRequest req) {
    User user = authService.register(req);
    String message = user.getUserName() + " sua conta foi criada com sucesso";
    return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterResponse(message));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(
      @RequestBody
      @Valid
      LoginRequest req, HttpServletResponse response) {
    AuthResult result = authService.login(req);
    CookieUtils.setRefreshCookie(response, result.refreshToken());
    return ResponseEntity.ok(result.authResponse());
  }

  @PostMapping("/refresh")
  public ResponseEntity<AuthResponse> refresh(
      @CookieValue(name = "refresh_token", required = false)
      String refreshToken, HttpServletResponse response) {
    AuthResult result = authService.refresh(refreshToken);
    CookieUtils.setRefreshCookie(response, result.refreshToken());
    return ResponseEntity.ok(result.authResponse());
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(
      @CookieValue(name = "refresh_token", required = false)
      String refreshToken, HttpServletResponse response) {
    authService.logout(refreshToken);
    CookieUtils.clearRefreshCookie(response);
    return ResponseEntity.noContent().build();
  }
}