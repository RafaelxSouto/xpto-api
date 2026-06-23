package br.com.xpto.modules.auth.dto.response;

import java.util.UUID;

public record AuthResponse(
    String accessToken,
    UUID userId,
    String role,
    String name
) { }
