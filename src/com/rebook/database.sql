-- USERS TABLE
DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       user_id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       phone VARCHAR(20),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ITEMS TABLE
DROP TABLE IF EXISTS items;

CREATE TABLE items (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       description TEXT,
                       quantity INT DEFAULT 1,
                       type ENUM('Exchange', 'Donation', 'Purchase') NOT NULL,
                       condition_status VARCHAR(50),
                       image_path VARCHAR(512),
                       user_id INT,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- TRANSACTIONS TABLE
DROP TABLE IF EXISTS transactions;

CREATE TABLE transactions (
                              transaction_id INT AUTO_INCREMENT PRIMARY KEY,
                              item_id INT NOT NULL,
                              receiver_name VARCHAR(255) NOT NULL,
                              receiver_contact VARCHAR(255),
                              transaction_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                              type ENUM('Exchange', 'Donation', 'Purchase') NOT NULL,
                              FOREIGN KEY (item_id) REFERENCES items(id) ON DELETE CASCADE
);
