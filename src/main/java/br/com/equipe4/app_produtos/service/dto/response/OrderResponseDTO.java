package br.com.equipe4.app_produtos.service.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
        Long id,
        Long userId,
        BigDecimal total,
        BigDecimal discount,
        BigDecimal freight,
        String status,
        LocalDateTime createdAt,
        String address,
        List<OrderItemResponseDTO> items) {
}
