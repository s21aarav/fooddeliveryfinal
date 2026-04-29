package com.fooddelivery.dao;

import com.fooddelivery.model.FoodOrder;
import com.fooddelivery.util.DBConnection;

import java.sql.*;
import java.util.*;

/**
 * Data Access Object (DAO) for FoodOrder
 * Handles all database operations for orders
 */
public class OrderDAO {

    /**
     * Add a new order to the database
     */
    public static boolean addOrder(FoodOrder order) {
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "INSERT INTO orders (orderId, customerName, restaurant, amount, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, order.getOrderId());
            ps.setString(2, order.getCustomerName());
            ps.setString(3, order.getRestaurant());
            ps.setDouble(4, order.getAmount());
            ps.setString(5, order.getStatus());

            int result = ps.executeUpdate();
            ps.close();

            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeConnection(connection);
        }
    }

    /**
     * Retrieve all orders from the database
     */
    public static List<FoodOrder> getAllOrders() {
        List<FoodOrder> orders = new ArrayList<>();
        Connection connection = null;

        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM orders";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                FoodOrder order = new FoodOrder(
                        rs.getString("orderId"),
                        rs.getString("customerName"),
                        rs.getString("restaurant"),
                        rs.getDouble("amount"),
                        rs.getString("status")
                );
                orders.add(order);
            }

            rs.close();
            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }

        return orders;
    }

    /**
     * Get all premium orders (amount > 1000)
     */
    public static List<FoodOrder> getPremiumOrders() {
        List<FoodOrder> premiumOrders = new ArrayList<>();
        Connection connection = null;

        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM orders WHERE amount > 1000";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                FoodOrder order = new FoodOrder(
                        rs.getString("orderId"),
                        rs.getString("customerName"),
                        rs.getString("restaurant"),
                        rs.getDouble("amount"),
                        rs.getString("status")
                );
                premiumOrders.add(order);
            }

            rs.close();
            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }

        return premiumOrders;
    }

    /**
     * Get total revenue from all orders
     */
    public static double calculateTotalRevenue() {
        double totalRevenue = 0;
        Connection connection = null;

        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT SUM(amount) as total FROM orders";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                totalRevenue = rs.getDouble("total");
            }

            rs.close();
            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }

        return totalRevenue;
    }

    /**
     * Get total number of orders
     */
    public static int getTotalOrderCount() {
        int count = 0;
        Connection connection = null;

        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT COUNT(*) as total FROM orders";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                count = rs.getInt("total");
            }

            rs.close();
            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }

        return count;
    }

    /**
     * Update order status
     */
    public static boolean updateOrderStatus(String orderId, String newStatus) {
        Connection connection = null;

        try {
            connection = DBConnection.getConnection();
            String sql = "UPDATE orders SET status = ? WHERE orderId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, newStatus);
            ps.setString(2, orderId);

            int result = ps.executeUpdate();
            ps.close();

            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeConnection(connection);
        }
    }

    /**
     * Delete an order
     */
    public static boolean deleteOrder(String orderId) {
        Connection connection = null;

        try {
            connection = DBConnection.getConnection();
            String sql = "DELETE FROM orders WHERE orderId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, orderId);

            int result = ps.executeUpdate();
            ps.close();

            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeConnection(connection);
        }
    }

    /**
     * Get an order by ID
     */
    public static FoodOrder getOrderById(String orderId) {
        Connection connection = null;

        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM orders WHERE orderId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                FoodOrder order = new FoodOrder(
                        rs.getString("orderId"),
                        rs.getString("customerName"),
                        rs.getString("restaurant"),
                        rs.getDouble("amount"),
                        rs.getString("status")
                );
                rs.close();
                ps.close();
                return order;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }

        return null;
    }
}
