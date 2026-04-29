package com.fooddelivery.servlet;

import com.fooddelivery.dao.OrderDAO;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * DeleteOrderServlet - Deletes an order
 * Maps to: /deleteOrder
 */
@WebServlet("/deleteOrder")
public class DeleteOrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String orderId = request.getParameter("orderId");

            if (orderId == null || orderId.trim().isEmpty()) {
                response.sendRedirect("viewOrders?error=Invalid Order ID");
                return;
            }

            // Delete order from database
            boolean isDeleted = OrderDAO.deleteOrder(orderId);

            if (isDeleted) {
                response.sendRedirect("viewOrders?success=Order deleted successfully");
            } else {
                response.sendRedirect("viewOrders?error=Failed to delete order");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("viewOrders?error=An error occurred");
        }
    }
}
