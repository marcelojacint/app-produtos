package br.com.equipe4.app_produtos.service.dto.response;

import java.math.BigDecimal;

public record OrderItemResponseDTO(
        Long id,
        Long productId,
        Integer quantity,
        BigDecimal priceSnapshot) {
}
