CREATE TABLE carts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    total_amount DECIMAL(19, 2) DEFAULT 0,
    updated_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES tb_usuario(id)
);

CREATE TABLE cart_items(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL,
    price_snapshot DECIMAL(19, 2) NOT NULL,
    FOREIGN KEY (cart_id) REFERENCES carts(id),
    FOREIGN KEY (product_id) REFERENCES tb_produto(id)
);
