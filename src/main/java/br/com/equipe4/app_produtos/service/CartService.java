package br.com.equipe4.app_produtos.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.equipe4.app_produtos.model.Cart;
import br.com.equipe4.app_produtos.model.CartItem;
import br.com.equipe4.app_produtos.model.Products;
import br.com.equipe4.app_produtos.model.User;
import br.com.equipe4.app_produtos.repository.CartItemRepository;
import br.com.equipe4.app_produtos.repository.CartRepository;
import br.com.equipe4.app_produtos.repository.ProductsRepository;
import br.com.equipe4.app_produtos.service.dto.CartItemRequestDTO;
import br.com.equipe4.app_produtos.service.dto.CartItemResponseDTO;
import br.com.equipe4.app_produtos.service.dto.CartResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductsRepository productsRepository;
    
    public Cart getOrCreateCart(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });
    }

    public CartResponseDTO getCartDTO(User user) {
        Cart cart = getOrCreateCart(user);
        return toCartDTO(cart);
    }

    @Transactional
    public CartResponseDTO addItem(User user, CartItemRequestDTO dto) {
        Cart cart = getOrCreateCart(user);
        Products product = productsRepository.findById(dto.productId())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
        
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + dto.quantity());
            item.setPriceSnapshot(product.getPrice()); 
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(dto.quantity());
            newItem.setPriceSnapshot(product.getPrice()); 
            cart.getItems().add(newItem);
        }

        cart.recalculateTotal();
        cartRepository.save(cart);
        return toCartDTO(cart);
    }

    @Transactional
    public CartResponseDTO updateItemQuantity(User user, Long itemId, Integer newQuantity) {
        Cart cart = getOrCreateCart(user);
        CartItem item = cartItemRepository.findById(itemId)
                .filter(i -> i.getCart().getId().equals(cart.getId())) 
                .orElseThrow(() -> new EntityNotFoundException("Item não encontrado no seu carrinho"));

        item.setQuantity(newQuantity);
        
        cart.recalculateTotal();
        cartRepository.save(cart);
        return toCartDTO(cart);
    }

    @Transactional
    public CartResponseDTO removeItem(User user, Long itemId) {
        Cart cart = getOrCreateCart(user);
        CartItem item = cartItemRepository.findById(itemId)
                .filter(i -> i.getCart().getId().equals(cart.getId()))
                .orElseThrow(() -> new EntityNotFoundException("Item não encontrado"));

        cart.getItems().remove(item);
        cartItemRepository.delete(item);
        
        cart.recalculateTotal();
        cartRepository.save(cart);
        return toCartDTO(cart);
    }

    private CartResponseDTO toCartDTO(Cart cart) {
        var itemsDto = cart.getItems().stream()
                .map(i -> new CartItemResponseDTO(
                        i.getId(),
                        i.getProduct().getId(),
                        i.getProduct().getName(),
                        i.getQuantity(),
                        i.getPriceSnapshot(),
                        i.getSubTotal()
                )).toList();

        return new CartResponseDTO(cart.getId(), cart.getTotalAmount(), itemsDto);
    }
   
}
