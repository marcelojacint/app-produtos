package br.com.equipe4.app_produtos.service.dto;

import java.math.BigDecimal;

public record CartItemResponseDTO(
        Long itemId,
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal priceSnapshot,
        BigDecimal subtotal) {

}
