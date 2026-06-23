package br.com.xpto.core.exceptions;

import java.time.Instant;

public record ErrorResponse(int status, String message, Instant timestamp) {

}