package br.com.equipe4.app_produtos.service;

import br.com.equipe4.app_produtos.model.*;
import br.com.equipe4.app_produtos.repository.*;
import br.com.equipe4.app_produtos.service.dto.CartItemDTO;
import br.com.equipe4.app_produtos.service.dto.CouponApplyDTO;
import br.com.equipe4.app_produtos.service.dto.CouponApplyResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponUsageRepository couponUsageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private CouponRepository repository;

    @Autowired
    private PromotionRepository promotionRepository;

    public Coupon createCoupon(br.com.equipe4.app_produtos.service.dto.CouponCreateDTO dto) {
        var promoOpt = promotionRepository.findById(dto.promotionId());
        if (promoOpt.isEmpty()) throw new IllegalArgumentException("Promotion not found");
        Coupon c = new Coupon();
        c.setCode(dto.code());
        c.setPromotion(promoOpt.get());
        c.setStartAt(dto.startAt());
        c.setEndAt(dto.endAt());
        c.setUsageLimit(dto.usageLimit());
        c.setUsedCount(0);
        return repository.save(c);
    }

    public CouponApplyResponseDTO applyCoupon(CouponApplyDTO dto) {
        var couponOpt = couponRepository.findByCode(dto.code());
        if (couponOpt.isEmpty()) {
            throw new IllegalArgumentException("Coupon not found");
        }

        Coupon coupon = couponOpt.get();
        LocalDateTime now = LocalDateTime.now();

        // Check coupon date range (coupon has priority, if null use promotion)
        LocalDateTime start = coupon.getStartAt() != null ? coupon.getStartAt() : coupon.getPromotion().getStartAt();
        LocalDateTime end = coupon.getEndAt() != null ? coupon.getEndAt() : coupon.getPromotion().getEndAt();

        if (start != null && now.isBefore(start)) {
            throw new IllegalArgumentException("Coupon not active yet");
        }
        if (end != null && now.isAfter(end)) {
            throw new IllegalArgumentException("Coupon expired");
        }

        // Check usage limit
        if (coupon.getUsageLimit() != null && coupon.getUsedCount() != null && coupon.getUsedCount() >= coupon.getUsageLimit()) {
            throw new IllegalArgumentException("Coupon usage limit reached");
        }

        var userOpt = userRepository.findById(dto.userId());
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        User user = userOpt.get();

        // Check if user already used
        if (couponUsageRepository.findByCouponAndUser(coupon, user).isPresent()) {
            throw new IllegalArgumentException("Coupon already used by user");
        }

        // Validate cart items and calculate totals
        List<CartItemDTO> items = dto.cart();
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal applicableTotal = BigDecimal.ZERO;

        for (CartItemDTO item : items) {
            var prodOpt = productsRepository.findById(item.productId());
            if (prodOpt.isEmpty()) {
                throw new IllegalArgumentException("Product not found: " + item.productId());
            }
            Products prod = prodOpt.get();
            BigDecimal line = prod.getPrice().multiply(BigDecimal.valueOf(item.quantity()));
            total = total.add(line);

            // Check if this product is eligible for the promotion
            Promotion promo = coupon.getPromotion();
            boolean eligible = false;
            if (promo.getProduct() != null && promo.getProduct().getId().equals(prod.getId())) {
                eligible = true;
            }
            if (promo.getCategory() != null && prod.getCategory() != null && promo.getCategory().getId().equals(prod.getCategory().getId())) {
                eligible = true;
            }
            // If promotion has neither product nor category, apply to all
            if (promo.getProduct() == null && promo.getCategory() == null) {
                eligible = true;
            }

            if (eligible) {
                applicableTotal = applicableTotal.add(line);
            }
        }

        if (applicableTotal.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Coupon does not apply to any products in the cart");
        }

        // Calculate discount
        BigDecimal discount = BigDecimal.ZERO;
        PromotionType type = coupon.getPromotion().getType();
        BigDecimal value = coupon.getPromotion().getValue();
        if (type == PromotionType.PERCENTAGE) {
            discount = applicableTotal.multiply(value).divide(BigDecimal.valueOf(100));
        } else if (type == PromotionType.FIXED) {
            // Fixed discount applied to applicable total, not exceeding it
            discount = value.min(applicableTotal);
        }

        BigDecimal newTotal = total.subtract(discount);
        if (newTotal.compareTo(BigDecimal.ZERO) < 0) newTotal = BigDecimal.ZERO;

        // Persist usage (increment usedCount and add usage record)
        coupon.setUsedCount((coupon.getUsedCount() == null ? 0 : coupon.getUsedCount()) + 1);
        repository.save(coupon);

        CouponUsage usage = new CouponUsage();
        usage.setCoupon(coupon);
        usage.setUser(user);
        usage.setUsedAt(LocalDateTime.now());
        couponUsageRepository.save(usage);

        return new CouponApplyResponseDTO(total, discount, newTotal);
    }
}
