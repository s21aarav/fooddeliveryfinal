package com.fooddelivery.servlet;

import com.fooddelivery.dao.OrderDAO;
import com.fooddelivery.model.FoodOrder;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * ViewOrdersServlet - Displays all orders
 * Maps to: /viewOrders
 */
@WebServlet("/viewOrders")
public class ViewOrdersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Get all orders from database
            List<FoodOrder> orders = OrderDAO.getAllOrders();

            // Store in request attribute
            request.setAttribute("orders", orders);

            // Forward to JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("viewOrders.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp?message=Error retrieving orders");
        }
    }
}
