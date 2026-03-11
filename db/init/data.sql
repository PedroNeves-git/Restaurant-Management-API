CREATE TABLE IF NOT EXISTS users_type (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          name VARCHAR(150) NOT NULL
    );

CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    login VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    type_id BIGINT NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME
    );

INSERT IGNORE INTO users_type (id, name) VALUES (1, 'Dono do Restaurante');
INSERT IGNORE INTO users_type (id, name) VALUES (2, 'Cliente');