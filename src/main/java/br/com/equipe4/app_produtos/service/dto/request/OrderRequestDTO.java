package br.com.equipe4.app_produtos.service.dto.request;

import br.com.equipe4.app_produtos.validation.Annotations.NaoNulo;
import br.com.equipe4.app_produtos.validation.Annotations.NaoVazioSeInformado;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequestDTO(
        @NaoNulo(message = "required field.")
        @NaoVazioSeInformado(message = "required field.")
        BigDecimal discount,
        @NaoNulo(message = "required field.")
        @NaoVazioSeInformado(message = "required field.")
        BigDecimal freight,
        @NaoNulo(message = "required field.")
        @NaoVazioSeInformado(message = "required field.")
        String address,
        List<OrderItemRequestDTO> items) {
}
