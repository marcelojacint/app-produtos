package br.com.equipe4.app_produtos.service.dto;

import br.com.equipe4.app_produtos.model.PromotionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PromotionDTO(
        String name,
        PromotionType type,
        BigDecimal value,
        Long productId,
        Long categoryId,
        LocalDateTime startAt,
        LocalDateTime endAt
) {

}
