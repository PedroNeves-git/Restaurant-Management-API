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
    role ENUM('RESTAURANT_OWNER', 'USER') NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME
    );

CREATE TABLE IF NOT EXISTS cuisine_type (
                                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            name VARCHAR(255) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS restaurant (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          name VARCHAR(255),
    address VARCHAR(255),
    opening_hours TIME,
    closing_time TIME,
    cuisine_type_id BIGINT,
    owner_id BIGINT
    );

CREATE TABLE IF NOT EXISTS menu_items (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          name VARCHAR(50) NOT NULL,
    description VARCHAR(150) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    available_only_in_restaurant BOOLEAN NOT NULL DEFAULT FALSE,
    image_path VARCHAR(50),
    restaurant_id BIGINT NOT NULL
    );

-- users_type
INSERT IGNORE INTO users_type (id, name) VALUES (1, 'Restaurant Owner');
INSERT IGNORE INTO users_type (id, name) VALUES (2, 'Customer');

-- users
INSERT IGNORE INTO users (name, email, login, password, active, type_id, role, created_at, updated_at)
VALUES ('Admin', 'admin@restaurant.com', 'admin', 'Admin@123', true, 1, 'RESTAURANT_OWNER', NOW(),NOW());

-- cuisine_type
INSERT IGNORE INTO cuisine_type (id, name) VALUES (1, 'Mexican');
INSERT IGNORE INTO cuisine_type (id, name) VALUES (2, 'Japanese');
INSERT IGNORE INTO cuisine_type (id, name) VALUES (3, 'Brazilian');
INSERT IGNORE INTO cuisine_type (id, name) VALUES (4, 'Thai');

-- restaurant
INSERT IGNORE INTO restaurant (id, name, address, opening_hours, closing_time, cuisine_type_id, owner_id)
VALUES (1, 'Sakura House', '123 Main Street', '11:00:00', '23:00:00', 2, 1);

-- menu_items
INSERT IGNORE INTO menu_items (id, name, description, price, available_only_in_restaurant, image_path, restaurant_id)
VALUES (1, 'Salmon Sushi', 'Fresh salmon over seasoned rice', 18.90, false, '/images/salmon-sushi.jpg', 1);