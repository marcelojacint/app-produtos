package br.com.equipe4.app_produtos.repository;

import br.com.equipe4.app_produtos.model.Coupon;
import br.com.equipe4.app_produtos.model.CouponUsage;
import br.com.equipe4.app_produtos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponUsageRepository extends JpaRepository<CouponUsage, Long> {
    Optional<CouponUsage> findByCouponAndUser(Coupon coupon, User user);
    long countByCoupon(Coupon coupon);
}
