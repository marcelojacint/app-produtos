package br.com.equipe4.app_produtos.service.dto;

import java.math.BigDecimal;
import java.util.List;

public record CartResponseDTO(
        Long cartId,
        BigDecimal totalAmount,
        List<CartItemResponseDTO> items) {

}
