CREATE TABLE users (
                       user_id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       phone VARCHAR(20),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE items (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       description TEXT,
                       price DOUBLE,
                       quantity INT DEFAULT 1,
                       image_path VARCHAR(512),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ;


CREATE TABLE transactions (
                              transaction_id INT AUTO_INCREMENT PRIMARY KEY,
                              item_id INTEGER NOT NULL,
                              receiver_name VARCHAR(255) NOT NULL,
                              receiver_contact VARCHAR(255),
                              transaction_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                              type VARCHAR(255) CHECK(type IN ('Exchange', 'Donation', 'Purchase')),
                              FOREIGN KEY(item_id) REFERENCES items(id)
);