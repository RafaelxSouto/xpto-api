package br.com.xpto.modules.auth.service;

import br.com.xpto.core.config.JwtProperties;
import br.com.xpto.modules.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;

@Service
@RequiredArgsConstructor
public class JwtService {

  private static final String ROLE_CLAIM = "role";
  private final JwtProperties props;

  public String generateAccessToken(User user) {
    Instant now = Instant.now();

    return Jwts.builder()
        .subject(user.getId().toString())
        .claim(ROLE_CLAIM, user.getRole().name())
        .issuedAt(Date.from(now))
        .expiration(Date.from(now.plusMillis(props.getExpiration())))
        .signWith(getSignKey())
        .compact();
  }

  public boolean isValid(String token) {
    try {
      getClaims(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  public String extractUserId(String token) {
    return getClaims(token).getSubject();
  }

  public String hashToken(String rawToken) {
    return DigestUtils.sha256Hex(rawToken);
  }

  private Claims getClaims(String token) {
    return Jwts.parser()
        .verifyWith(getSignKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  private SecretKey getSignKey() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(props.getSecret()));
  }
}