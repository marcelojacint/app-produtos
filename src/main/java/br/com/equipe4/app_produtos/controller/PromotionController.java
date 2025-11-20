package br.com.equipe4.app_produtos.controller;

import br.com.equipe4.app_produtos.model.Category;
import br.com.equipe4.app_produtos.model.Products;
import br.com.equipe4.app_produtos.model.Promotion;
import br.com.equipe4.app_produtos.model.PromotionStatus;
import br.com.equipe4.app_produtos.service.PromotionService;
import br.com.equipe4.app_produtos.repository.ProductsRepository;
import br.com.equipe4.app_produtos.repository.CategoryRepository;
import br.com.equipe4.app_produtos.service.dto.PromotionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/promotions")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping
    public ResponseEntity<Promotion> create(@RequestBody @Valid PromotionDTO dto) {
        Promotion p = new Promotion();
        p.setName(dto.name());
        p.setType(dto.type());
        p.setValue(dto.value());
        p.setStartAt(dto.startAt());
        p.setEndAt(dto.endAt());

        if (dto.productId() != null) {
            var prodOpt = productsRepository.findById(dto.productId());
            prodOpt.ifPresent(p::setProduct);
        }
        if (dto.categoryId() != null) {
            var catOpt = categoryRepository.findById(dto.categoryId());
            catOpt.ifPresent(p::setCategory);
        }

        Promotion saved = promotionService.save(p);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Promotion>> listActive() {
        List<Promotion> promotions = promotionService.findByStatus(PromotionStatus.ACTIVE);
        return ResponseEntity.ok(promotions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Promotion> getById(@PathVariable Long id) {
        var promo = promotionService.findById(id);
        if (promo.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(promo.get());
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Promotion> deactivate(@PathVariable Long id) {
        var promo = promotionService.findById(id);
        if (promo.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Promotion p = promo.get();
        p.setStatus(PromotionStatus.INACTIVE);
        Promotion updated = promotionService.save(p);
        return ResponseEntity.ok(updated);
    }
}
