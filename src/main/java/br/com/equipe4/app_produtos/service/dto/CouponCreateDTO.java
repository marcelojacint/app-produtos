package br.com.equipe4.app_produtos.service.dto;

import java.time.LocalDateTime;

public record CouponCreateDTO(String code, Long promotionId, LocalDateTime startAt, LocalDateTime endAt, Integer usageLimit) {

}
