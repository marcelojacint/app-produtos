-- cria promoções, cupons e tabela de uso de cupons
CREATE TABLE promotions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(50),
    amount DECIMAL(19,2) NOT NULL,
    product_id BIGINT,
    category_id BIGINT,
    start_at TIMESTAMP,
    end_at TIMESTAMP
);

CREATE TABLE coupons (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(100) NOT NULL UNIQUE,
    promotion_id BIGINT NOT NULL,
    start_at TIMESTAMP,
    end_at TIMESTAMP,
    usage_limit INT,
    used_count INT
);

CREATE TABLE coupon_usages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    coupon_id BIGINT NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    used_at TIMESTAMP
);

-- Observação: as tabelas de produto/categoria/usuários podem ser criadas por outras migrações; evite adicionar chaves estrangeiras a elas aqui para evitar problemas na ordem das migrações.

ALTER TABLE coupons ADD CONSTRAINT fk_coupons_promotion FOREIGN KEY (promotion_id) REFERENCES promotions(id);
ALTER TABLE coupon_usages ADD CONSTRAINT fk_coupon_usages_coupon FOREIGN KEY (coupon_id) REFERENCES coupons(id);
