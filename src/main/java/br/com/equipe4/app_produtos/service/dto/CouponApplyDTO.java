package br.com.equipe4.app_produtos.service.dto;

import java.util.List;

public record CouponApplyDTO(String code, String userId, List<CartItemDTO> cart) {

}
