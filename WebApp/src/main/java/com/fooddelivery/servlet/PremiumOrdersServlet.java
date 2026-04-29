package com.fooddelivery.servlet;

import com.fooddelivery.dao.OrderDAO;
import com.fooddelivery.model.FoodOrder;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * PremiumOrdersServlet - Displays orders with amount > 1000
 * Maps to: /premiumOrders
 */
@WebServlet("/premiumOrders")
public class PremiumOrdersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Get premium orders from database
            List<FoodOrder> premiumOrders = OrderDAO.getPremiumOrders();

            // Store in request attribute
            request.setAttribute("orders", premiumOrders);
            request.setAttribute("title", "💎 Premium Orders (Amount > ₹1000)");

            // Forward to JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("viewOrders.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp?message=Error retrieving premium orders");
        }
    }
}
