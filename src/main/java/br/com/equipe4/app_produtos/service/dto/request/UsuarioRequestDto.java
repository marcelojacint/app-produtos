package br.com.equipe4.app_produtos.service.dto.request;

import jakarta.validation.constraints.Email;

public record UsuarioRequestDto(
        String nome,
        @Email(message = "Digite um email válido!")
        String email,
        String senha) {
}
