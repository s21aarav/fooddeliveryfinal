package com.fooddelivery.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/fooddb?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8";

    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "sahasra@1709";

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER_CLASS);
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found!", e);
        } catch (SQLException e) {
            throw new RuntimeException("Database connection failed!", e);
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }
}