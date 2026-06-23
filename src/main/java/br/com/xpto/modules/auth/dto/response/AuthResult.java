package br.com.xpto.modules.auth.dto.response;

public record AuthResult(AuthResponse authResponse, String refreshToken) {

}