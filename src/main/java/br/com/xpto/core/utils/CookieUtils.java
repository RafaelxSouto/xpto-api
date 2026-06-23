package br.com.xpto.core.utils;


import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

public final class CookieUtils {

  private static final String REFRESH_COOKIE = "refresh_token";
  private static final String PATH = "/api/auth";
  private static final long MAX_AGE_DAYS = 30;

  private CookieUtils() {
  }

  public static void setRefreshCookie(HttpServletResponse response, String token) {
    ResponseCookie cookie = ResponseCookie.from(REFRESH_COOKIE, token).httpOnly(true).secure(true)
        .path(PATH).maxAge(Duration.ofDays(MAX_AGE_DAYS)).build();
    response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
  }

  public static void clearRefreshCookie(HttpServletResponse response) {
    ResponseCookie cookie = ResponseCookie.from(REFRESH_COOKIE, "").httpOnly(true).secure(true)
        .path(PATH).maxAge(Duration.ZERO).build();
    response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
  }
}