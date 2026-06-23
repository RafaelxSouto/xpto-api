package br.com.xpto.modules.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record RegisterRequest(
    @NotBlank @Size(min = 3, max = 100)
    @Pattern(regexp = "^[\\p{L} '\\-]+$", message = "Nome não pode conter números ou caracteres especiais")
    String fullName,

    @NotBlank @Email @Size(max = 100)
    String email,

    @NotBlank @Size(min = 8, max = 100)
    String password,

    @NotBlank @Pattern(regexp = "^[0-9]{11}$")
    String cpf,

    @Size(max = 20)
    String phone,

    @NotNull @Past(message = "Data de nascimento inválida")
    LocalDate birthDate) {

}
