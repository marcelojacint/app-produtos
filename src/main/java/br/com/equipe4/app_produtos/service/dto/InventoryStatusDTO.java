package br.com.equipe4.app_produtos.service.dto;

public record InventoryStatusDTO(
    Long productId,
    String productName,
    Integer currentQuantity,
    Integer minLevel,
    boolean isLowStock
) {

}
