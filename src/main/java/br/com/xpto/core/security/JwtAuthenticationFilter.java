package br.com.xpto.core.security;

import br.com.xpto.modules.auth.service.JwtService;
import br.com.xpto.modules.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserRepository userRepository;

  @Override
  protected void doFilterInternal(
      @NonNull
      HttpServletRequest request,
      @NonNull
      HttpServletResponse response,
      @NonNull
      FilterChain filterChain
  ) throws ServletException, IOException {

    String header = request.getHeader("Authorization");

    if (header == null || !header.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = header.substring(7);

    if (!jwtService.isValid(token)) {
      filterChain.doFilter(request, response);
      return;
    }

    UUID userId = UUID.fromString(jwtService.extractUserId(token));

    userRepository.findById(userId).ifPresent(user -> {
      var auth = new UsernamePasswordAuthenticationToken(
          user, null, user.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(auth);
    });

    filterChain.doFilter(request, response);
  }
}