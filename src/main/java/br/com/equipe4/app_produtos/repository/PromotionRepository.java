package br.com.equipe4.app_produtos.repository;

import br.com.equipe4.app_produtos.model.Promotion;
import br.com.equipe4.app_produtos.model.PromotionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    List<Promotion> findByStatus(PromotionStatus status);
}
