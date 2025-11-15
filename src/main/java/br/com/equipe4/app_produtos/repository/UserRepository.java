package br.com.equipe4.app_produtos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.equipe4.app_produtos.model.User;

public interface UserRepository extends JpaRepository<User, String> {

    UserDetails findByLogin(String login);

}
