package br.com.equipe4.app_produtos.service;

import br.com.equipe4.app_produtos.model.Promotion;
import br.com.equipe4.app_produtos.model.PromotionStatus;
import br.com.equipe4.app_produtos.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    public Promotion save(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    public Optional<Promotion> findById(Long id) {
        return promotionRepository.findById(id);
    }

    public List<Promotion> findAll() {
        return promotionRepository.findAll();
    }

    public List<Promotion> findByStatus(PromotionStatus status) {
        return promotionRepository.findByStatus(status);
    }

    public void delete(Long id) {
        promotionRepository.deleteById(id);
    }
}
