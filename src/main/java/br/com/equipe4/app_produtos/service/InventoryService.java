package br.com.equipe4.app_produtos.service;

import javax.management.RuntimeErrorException;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.equipe4.app_produtos.model.Inventory;
import br.com.equipe4.app_produtos.model.InventoryTransaction;
import br.com.equipe4.app_produtos.model.Products;
import br.com.equipe4.app_produtos.model.User;
import br.com.equipe4.app_produtos.repository.InventoryRepository;
import br.com.equipe4.app_produtos.repository.InventoryTransactionRepository;
import br.com.equipe4.app_produtos.repository.ProductsRepository;
import br.com.equipe4.app_produtos.service.dto.InventoryStatusDTO;
import br.com.equipe4.app_produtos.service.dto.StockMovementDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryTransactionRepository inventoryTransactionRepository;
    private final ProductsRepository productsRepository;

    public InventoryStatusDTO getInventoryStatus(Long productId) {
        Inventory inventory = findInventoryByProductId(productId);
        return new InventoryStatusDTO(
            productId,
            inventory.getProduct().getName(),
            inventory.getQuantity(),
            inventory.getMinLevel(),
            inventory.isLowStock()
        );
    }

    @Transactional
    public InventoryStatusDTO processTransaction(Long productId, StockMovementDTO dto, Authentication auth) {
        Inventory inventory = findInventoryByProductId(productId);
        User user = (User) auth.getPrincipal();

        // 1. Calcular novo saldo
        int currentQuantity = inventory.getQuantity();
        int moveQuantity = dto.quantity();
        int newQuantity = currentQuantity;

        switch (dto.type()) {
            case ENTRY, RETURN -> newQuantity += moveQuantity;
            case EXIT -> {
                if (currentQuantity < moveQuantity) {
                    throw new RuntimeException("Estoque insuficiente. Atual: " + currentQuantity + ", Solicitado: " + moveQuantity);
                }
                newQuantity -= moveQuantity;
        }
            case ADJUSTMENT -> {
                newQuantity = moveQuantity;
            }
        }

        // 2. Atualizar Estoque
        inventory.setQuantity(newQuantity);
        inventoryRepository.save(inventory);

        // 3. Registrar Histórico
        InventoryTransaction transaction = new InventoryTransaction();
        transaction.setInventory(inventory);
        transaction.setType(dto.type());
        transaction.setQuantity(moveQuantity);
        transaction.setReason(dto.reason());
        transaction.setPerformedBy(user);
        inventoryTransactionRepository.save(transaction);

        return getInventoryStatus(productId);
    }

    public void initializeInventory(Products product) {
        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setQuantity(0);
        inventory.setMinLevel(5);
        inventoryRepository.save(inventory);
        
    }

    private Inventory findInventoryByProductId(Long productId) {
        return productsRepository.findById(productId)
            .map(Products::getInventory)
            .orElseThrow(() -> new EntityNotFoundException("Estoque não encontrado para o produto ID: " + productId));
    }
}
