-- =====================================================
-- PostgreSQL schema for Food Delivery App
-- Run this against the `fooddb` database (psql -d fooddb -f fooddb_postgres.sql)
-- =====================================================

-- =====================================================
-- Create users table
-- =====================================================
CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    fullname VARCHAR(100),
    createdat TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lastlogin TIMESTAMP
);

-- =====================================================
-- Create orders table
-- =====================================================
CREATE TABLE IF NOT EXISTS orders (
    orderid VARCHAR(50) PRIMARY KEY,
    customername VARCHAR(100) NOT NULL,
    restaurant VARCHAR(100) NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    status VARCHAR(50) NOT NULL,
    createdat TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedat TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Indexes
CREATE INDEX IF NOT EXISTS idx_customer_name ON orders(customername);
CREATE INDEX IF NOT EXISTS idx_restaurant ON orders(restaurant);
CREATE INDEX IF NOT EXISTS idx_status ON orders(status);
CREATE INDEX IF NOT EXISTS idx_amount ON orders(amount);
CREATE INDEX IF NOT EXISTS idx_created_at ON orders(createdat);

-- Sample data (idempotent)
INSERT INTO users (username, password, email, fullname)
VALUES ('admin', 'admin123', 'admin@fooddelivery.com', 'Administrator')
ON CONFLICT (username) DO NOTHING;

INSERT INTO orders (orderid, customername, restaurant, amount, status) VALUES
('ORD001', 'Rajesh Kumar', 'Dominoes', 850.50, 'Delivered'),
('ORD002', 'Priya Singh', 'Swiggy', 1200.00, 'On the Way'),
('ORD003', 'Amit Patel', 'Zomato', 650.75, 'Preparing'),
('ORD004', 'Neha Sharma', 'Biryani House', 1450.00, 'Pending'),
('ORD005', 'Sanjay Verma', 'Pizza Hut', 799.99, 'Delivered')
ON CONFLICT (orderid) DO NOTHING;

-- View for premium orders
CREATE OR REPLACE VIEW premium_orders AS
SELECT * FROM orders WHERE amount > 1000;

-- Stored function to get order statistics
CREATE OR REPLACE FUNCTION sp_get_order_statistics()
RETURNS TABLE(
    total_orders bigint,
    total_revenue double precision,
    average_order_value double precision,
    max_order_value double precision,
    min_order_value double precision
) AS $$
BEGIN
    RETURN QUERY
    SELECT COUNT(*)::bigint, SUM(amount)::double precision, AVG(amount)::double precision, MAX(amount)::double precision, MIN(amount)::double precision
    FROM orders;
END;
$$ LANGUAGE plpgsql;
