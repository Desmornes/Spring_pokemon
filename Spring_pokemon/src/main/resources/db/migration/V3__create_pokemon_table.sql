CREATE TABLE IF NOT EXISTS pokemon (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    base_experience INT NOT NULL
    );
