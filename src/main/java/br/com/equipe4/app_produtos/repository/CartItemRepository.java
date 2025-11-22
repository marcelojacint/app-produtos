package br.com.equipe4.app_produtos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.equipe4.app_produtos.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
