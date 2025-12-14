CREATE DATABASE IF NOT EXISTS robot_delivery;
USE robot_delivery;

CREATE TABLE robots (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    robot_id VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL,
    current_x INT,
    current_y INT,
    battery_level INT,
    last_heartbeat TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_robot_id (robot_id)
);

CREATE TABLE sensor_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    robot_id BIGINT NOT NULL,
    position_x INT NOT NULL DEFAULT 0,
    position_y INT NOT NULL DEFAULT 0,
    left_obstacle BOOLEAN NOT NULL DEFAULT FALSE,
    front_obstacle BOOLEAN NOT NULL DEFAULT FALSE,
    right_obstacle BOOLEAN NOT NULL DEFAULT FALSE,
    battery_level INT NOT NULL DEFAULT 100,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (robot_id) REFERENCES robots(id) ON DELETE CASCADE,
    INDEX idx_robot_timestamp (robot_id, timestamp)
);

CREATE TABLE deliveries (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    robot_id BIGINT NOT NULL,
    start_x INT NOT NULL,
    start_y INT NOT NULL,
    destination_x INT NOT NULL,
    destination_y INT NOT NULL,
    status VARCHAR(20) NOT NULL,
    distance_traveled INT DEFAULT 0,
    obstacles_encountered INT DEFAULT 0,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (robot_id) REFERENCES robots(id) ON DELETE CASCADE,
    INDEX idx_status (status)
);