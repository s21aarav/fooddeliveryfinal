package com.fooddelivery.servlet;

import com.fooddelivery.dao.OrderDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * DashboardServlet - Displays dashboard with statistics
 * Maps to: /dashboard
 */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Get statistics from database
            int totalOrders = OrderDAO.getTotalOrderCount();
            double totalRevenue = OrderDAO.calculateTotalRevenue();

            // Store in request attributes
            request.setAttribute("totalOrders", totalOrders);
            request.setAttribute("totalRevenue", totalRevenue);

            // Forward to JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("dashboard.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp?message=Error loading dashboard");
        }
    }
}
