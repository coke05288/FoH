DROP TABLE IF EXISTS users, user_login_history;

CREATE TABLE users
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    password        VARCHAR(255) NOT NULL,
    email           VARCHAR(255) NOT NULL UNIQUE,
    age             INTEGER NOT NULL,
    gender          VARCHAR(20),
    name            VARCHAR(255),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE user_login_history
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id         BIGINT NOT NULL,
    login_time      TIMESTAMP,
    ip_address      VARCHAR(255),
    FOREIGN KEY     (user_id) REFERENCES users (id)
);