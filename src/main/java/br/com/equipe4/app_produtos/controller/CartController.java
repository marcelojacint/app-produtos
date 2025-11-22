package br.com.equipe4.app_produtos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.equipe4.app_produtos.model.User;
import br.com.equipe4.app_produtos.service.CartService;
import br.com.equipe4.app_produtos.service.dto.CartItemRequestDTO;
import br.com.equipe4.app_produtos.service.dto.CartResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartResponseDTO> getCart(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(cartService.getCartDTO(user));
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponseDTO> addItem(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid CartItemRequestDTO dto) {
        return ResponseEntity.ok(cartService.addItem(user, dto));
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<CartResponseDTO> updateItem(
            @AuthenticationPrincipal User user,
            @PathVariable Long itemId,
            @RequestBody @Valid CartItemRequestDTO dto) {
        return ResponseEntity.ok(cartService.updateItemQuantity(user, itemId, dto.quantity()));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<CartResponseDTO> removeItem(
            @AuthenticationPrincipal User user,
            @PathVariable Long itemId) {
        return ResponseEntity.ok(cartService.removeItem(user, itemId));
    }

}
