package br.com.equipe4.app_produtos.service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CartItemRequestDTO(
        @NotNull Long productId,
        @NotNull @Min(1) Integer quantity) {

}
