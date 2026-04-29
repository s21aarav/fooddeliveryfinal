package com.fooddelivery.servlet;

import com.fooddelivery.dao.OrderDAO;
import com.fooddelivery.model.FoodOrder;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * AddOrderServlet - Handles adding new orders
 * Maps to: /addOrder
 */
@WebServlet("/addOrder")
public class AddOrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Get parameters from the form
            String orderId = request.getParameter("orderId");
            String customerName = request.getParameter("customerName");
            String restaurant = request.getParameter("restaurant");
            String amountStr = request.getParameter("amount");
            String status = request.getParameter("status");

            // Validation
            if (orderId == null || orderId.trim().isEmpty() ||
                customerName == null || customerName.trim().isEmpty() ||
                restaurant == null || restaurant.trim().isEmpty() ||
                amountStr == null || amountStr.trim().isEmpty() ||
                status == null || status.trim().isEmpty()) {

                response.sendRedirect("index.jsp?error=All fields are required");
                return;
            }

            // Parse amount
            double amount;
            try {
                amount = Double.parseDouble(amountStr);
                if (amount <= 0) {
                    response.sendRedirect("index.jsp?error=Amount must be greater than zero");
                    return;
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("index.jsp?error=Invalid amount value");
                return;
            }

            // Create FoodOrder object
            FoodOrder order = new FoodOrder(orderId, customerName, restaurant, amount, status);

            // Add order to database using DAO
            boolean isAdded = OrderDAO.addOrder(order);

            if (isAdded) {
                response.sendRedirect("index.jsp?success=Order added successfully!");
            } else {
                response.sendRedirect("index.jsp?error=Failed to add order");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp?error=An error occurred: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    }
}
