CREATE TABLE IF NOT EXISTS payments
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id         BIGINT NOT NULL,
    amount           DECIMAL(10,2) NOT NULL,
    payment_method   VARCHAR(255) NOT NULL,
    status           VARCHAR(255) NOT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
