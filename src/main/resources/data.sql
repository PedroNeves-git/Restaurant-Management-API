INSERT IGNORE INTO users_type (id, name) VALUES (1, 'Dono do Restaurante');
INSERT IGNORE INTO users_type (id, name) VALUES (2, 'Cliente');

INSERT IGNORE INTO users (name, email, login, password, active, type_id, created_at)
VALUES ('Admin', 'admin@restaurant.com', 'admin', 'Teste@123', true, 1, NOW());