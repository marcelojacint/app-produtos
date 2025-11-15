package br.com.equipe4.app_produtos.service.dto;

import br.com.equipe4.app_produtos.model.UserRole;

public record UserResponseDTO(String id, String login, UserRole role) {

}

