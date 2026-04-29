-- =====================================================
-- Food Delivery Order Management System - Database
-- =====================================================

-- Create Database
CREATE DATABASE IF NOT EXISTS fooddb;

USE fooddb;

-- =====================================================
-- Create users table (for authentication)
-- =====================================================

CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(50) PRIMARY KEY COMMENT 'Unique username',
    password VARCHAR(100) NOT NULL COMMENT 'User password (should be hashed in production)',
    email VARCHAR(100) COMMENT 'User email address',
    fullName VARCHAR(100) COMMENT 'Full name of user',
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Account creation time',
    lastLogin TIMESTAMP COMMENT 'Last login timestamp'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Users table for authentication';

-- =====================================================
-- Create orders table
-- =====================================================

CREATE TABLE IF NOT EXISTS orders (
    orderId VARCHAR(50) PRIMARY KEY COMMENT 'Unique Order Identifier',
    customerName VARCHAR(100) NOT NULL COMMENT 'Name of the customer',
    restaurant VARCHAR(100) NOT NULL COMMENT 'Restaurant name',
    amount DOUBLE NOT NULL COMMENT 'Order amount in rupees',
    status VARCHAR(50) NOT NULL COMMENT 'Current order status',
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Order creation timestamp',
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last update timestamp'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Orders table for Food Delivery System';

-- =====================================================
-- Create indexes for better query performance
-- =====================================================

CREATE INDEX idx_customer_name ON orders(customerName);
CREATE INDEX idx_restaurant ON orders(restaurant);
CREATE INDEX idx_status ON orders(status);
CREATE INDEX idx_amount ON orders(amount);
CREATE INDEX idx_created_at ON orders(createdAt);

-- =====================================================
-- Sample data (optional - for testing)
-- =====================================================

-- Insert default admin user
INSERT INTO users (username, password, email, fullName) VALUES
('admin', 'admin123', 'admin@fooddelivery.com', 'Administrator');

-- Insert sample orders
INSERT INTO orders (orderId, customerName, restaurant, amount, status) VALUES
('ORD001', 'Rajesh Kumar', 'Dominoes', 850.50, 'Delivered'),
('ORD002', 'Priya Singh', 'Swiggy', 1200.00, 'On the Way'),
('ORD003', 'Amit Patel', 'Zomato', 650.75, 'Preparing'),
('ORD004', 'Neha Sharma', 'Biryani House', 1450.00, 'Pending'),
('ORD005', 'Sanjay Verma', 'Pizza Hut', 799.99, 'Delivered');

-- =====================================================
-- View for Premium Orders
-- =====================================================

CREATE VIEW premium_orders AS
SELECT * FROM orders WHERE amount > 1000;

-- =====================================================
-- Stored Procedure to get order statistics
-- =====================================================

DELIMITER $$

CREATE PROCEDURE sp_get_order_statistics()
BEGIN
    SELECT 
        COUNT(*) as total_orders,
        SUM(amount) as total_revenue,
        AVG(amount) as average_order_value,
        MAX(amount) as max_order_value,
        MIN(amount) as min_order_value
    FROM orders;
END$$

DELIMITER ;

-- =====================================================
-- Display table structure
-- =====================================================

SHOW TABLES;
DESCRIBE orders;

-- =====================================================
-- Verify sample data
-- =====================================================

SELECT * FROM orders;
SELECT COUNT(*) as total_orders FROM orders;
SELECT SUM(amount) as total_revenue FROM orders;
