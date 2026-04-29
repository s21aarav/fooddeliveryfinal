package com.fooddelivery.util;

public class TestDB {
    public static void main(String[] args) {
        try {
            DBConnection.getConnection();
            System.out.println("TEST_SUCCESS");
        } catch (Exception e) {
            System.out.println("TEST_FAILED");
            e.printStackTrace();
        }
    }
}
