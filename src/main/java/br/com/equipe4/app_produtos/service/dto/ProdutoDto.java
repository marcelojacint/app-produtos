package br.com.equipe4.app_produtos.service.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProdutoDto(Long id, String codigoBarras, String nome, BigDecimal preco) {
}
