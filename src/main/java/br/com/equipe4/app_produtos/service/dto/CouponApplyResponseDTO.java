package br.com.equipe4.app_produtos.service.dto;

import java.math.BigDecimal;

public record CouponApplyResponseDTO(BigDecimal originalTotal, BigDecimal discount, BigDecimal newTotal) {

}
