package br.com.equipe4.app_produtos.service.dto;

import br.com.equipe4.app_produtos.model.TransactionType;
import jakarta.validation.constraints.NotNull;

public record StockMovementDTO(
        @NotNull(message = "Tipo é obrigatório")
        TransactionType type,
        
        @NotNull(message = "Quantidade deve ser positiva")
        Integer quantity,
        
        String reason
    )
{

}
