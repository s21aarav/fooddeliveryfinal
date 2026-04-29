package com.fooddelivery.model;

public class FoodOrder {
    private String orderId;
    private String customerName;
    private String restaurant;
    private double amount;
    private String status;

    // Default Constructor
    public FoodOrder() {
    }

    // Parameterized Constructor
    public FoodOrder(String orderId, String customerName, String restaurant, double amount, String status) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.restaurant = restaurant;
        this.amount = amount;
        this.status = status;
    }

    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("Order ID: %-5s | Customer: %-15s | Restaurant: %-15s | Amount: ₹%-7.2f | Status: %s",
                orderId, customerName, restaurant, amount, status);
    }
}
