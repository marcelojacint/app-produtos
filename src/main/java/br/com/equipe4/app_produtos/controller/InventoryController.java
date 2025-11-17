package br.com.equipe4.app_produtos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.equipe4.app_produtos.model.TransactionType;
import br.com.equipe4.app_produtos.service.InventoryService;
import br.com.equipe4.app_produtos.service.ProductsService;
import br.com.equipe4.app_produtos.service.dto.InventoryStatusDTO;
import br.com.equipe4.app_produtos.service.dto.StockMovementDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    private final ProductsService productsService;

    @GetMapping("/{productId}")
    public ResponseEntity<InventoryStatusDTO> getStatus(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getInventoryStatus(productId));
    }

    @PostMapping("/{productId}/add")
    @PreAuthorize("hasRole('ADMIN') or @productsService.isOwner(authentication, #prodcutId)")
    public ResponseEntity<InventoryStatusDTO> addStock(
            @PathVariable Long productId,
            @RequestBody @Valid StockMovementDTO dto,
            Authentication authentication) {
        
        if (dto.type() != TransactionType.ENTRY && dto.type() != TransactionType.RETURN) {
            // Opcional: forçar ENTRY aqui se quiser separar estritamente as URLs
            return ResponseEntity.badRequest().build(); 
        }
        
        return ResponseEntity.ok(inventoryService.processTransaction(productId, dto, authentication));
    }

    @PostMapping("/{productId}/remove")
    @PreAuthorize("hasRole('ADMIN') or @productsService.isOwner(authentication, #productId)")
    public ResponseEntity<InventoryStatusDTO> removeStock(
            @PathVariable Long productId,
            @RequestBody @Valid StockMovementDTO dto,
            Authentication authentication) {

        if (dto.type() != TransactionType.EXIT) {
             // Opcional: forçar EXIT
             return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(inventoryService.processTransaction(productId, dto, authentication));
    }

    // Endpoint genérico para ajustes
    @PostMapping("/{productId}/transaction")
    @PreAuthorize("hasRole('ADMIN') or @productsService.isOwner(authentication, #productId)")
    public ResponseEntity<InventoryStatusDTO> transaction(
            @PathVariable Long productId,
            @RequestBody @Valid StockMovementDTO dto,
            Authentication authentication) {
        return ResponseEntity.ok(inventoryService.processTransaction(productId, dto, authentication));
    }

}
