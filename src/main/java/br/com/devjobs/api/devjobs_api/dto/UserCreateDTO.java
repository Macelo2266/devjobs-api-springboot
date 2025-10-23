package br.com.devjobs.api.devjobs_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserCreateDTO {

    @NotBlank(message = " O nome é obrigatório")
    private String name;
    @NotBlank(message = "O nome do usuário é obrigatório")
    private String username;

    @Email(message = "O e-mail deve ser válido")
    @NotBlank(message = "O e-mail é obrigatório")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    private String password;

    @NotBlank(message = "O perfil (role) é obrigatório")
    private String role;
}
