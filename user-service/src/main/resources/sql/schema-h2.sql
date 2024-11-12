DROP TABLE IF EXISTS users, user_login_history;

CREATE TABLE users
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(100) NOT NULL,
    email           VARCHAR(100) NOT NULL UNIQUE,
    age             INT NOT NULL,
    password_hash   VARCHAR(255) NOT NULL,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT    CURRENT_TIMESTAMP
);

CREATE TABLE user_login_history
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id         BIGINT NOT NULL,
    login_time      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ip_address      VARCHAR(45) NULL,
    FOREIGN KEY     (user_id) REFERENCES users (id)
);