package br.com.equipe4.app_produtos.service.dto.request;

import br.com.equipe4.app_produtos.validation.Annotations.NaoNulo;
import br.com.equipe4.app_produtos.validation.Annotations.NaoVazioSeInformado;

import java.math.BigDecimal;

public record OrderItemRequestDTO(
        @NaoNulo(message = "required field.")
        @NaoVazioSeInformado(message = "required field.")
        Integer quantity,
        @NaoNulo(message = "required field.")
        @NaoVazioSeInformado(message = "required field.")
        BigDecimal priceSnapshot) {
}
