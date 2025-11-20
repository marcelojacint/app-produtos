package br.com.equipe4.app_produtos.controller;

import br.com.equipe4.app_produtos.service.CouponService;
import br.com.equipe4.app_produtos.service.dto.CouponApplyDTO;
import br.com.equipe4.app_produtos.service.dto.CouponCreateDTO;
import br.com.equipe4.app_produtos.model.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping("/apply")
    public ResponseEntity apply(@RequestBody @Valid CouponApplyDTO dto) {
        var resp = couponService.applyCoupon(dto);
        return ResponseEntity.ok(resp);
    }

    @PostMapping
    public ResponseEntity<Coupon> create(@RequestBody @Valid CouponCreateDTO dto) {
        var created = couponService.createCoupon(dto);
        return ResponseEntity.ok(created);
    }
}
