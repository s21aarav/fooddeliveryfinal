package com.fooddelivery.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * LogoutServlet - Handles user logout
 * Maps to: /logout
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Get current session
            HttpSession session = request.getSession(false);
            
            if (session != null) {
                String username = (String) session.getAttribute("user");
                System.out.println("✅ User logged out: " + username);
                
                // Invalidate session
                session.invalidate();
            }
            
            // Redirect to login page
            response.sendRedirect("login.jsp?message=You have been logged out successfully");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=Error during logout");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
