package br.com.xpto.modules.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record RegisterRequest(
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    @Pattern(regexp = "^[\\p{L} '\\-]+$", message = "Nome não pode conter números ou caracteres especiais")
    String fullName,

    @NotBlank(message = "Nome de usuário é obrigatório")
    @Size(min = 3, max = 30, message = "Nome de usuário deve ter entre 3 e 30 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9._\\-]+$", message = "Nome de usuário só pode conter letras, números, ponto, underscore ou hífen")
    String userName,

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail em formato inválido")
    @Size(max = 100, message = "E-mail deve ter no máximo 100 caracteres")
    String email,

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 8, max = 100, message = "Senha deve ter no mínimo 8 caracteres")
    String password,

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "^[0-9]{11}$", message = "CPF deve conter exatamente 11 dígitos numéricos")
    String cpf,

    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    String phone,

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento inválida")
    LocalDate birthDate) {

}
