package br.com.equipe4.app_produtos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.equipe4.app_produtos.model.Cart;
import br.com.equipe4.app_produtos.model.User;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);

}
