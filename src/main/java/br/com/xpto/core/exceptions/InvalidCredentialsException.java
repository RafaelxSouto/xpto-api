package br.com.xpto.core.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends ApiException {

  public InvalidCredentialsException() {
    super("Email ou senha incorretos", HttpStatus.UNAUTHORIZED);
  }
}