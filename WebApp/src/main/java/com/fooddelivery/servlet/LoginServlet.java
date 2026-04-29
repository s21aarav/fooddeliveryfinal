package com.fooddelivery.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * LoginServlet - Handles user authentication
 * Maps to: /login
 * 
 * Default Credentials:
 * Username: admin
 * Password: admin123
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect("dashboard");
            return;
        }
        
        // Forward to login page
        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            // Input validation
            if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
                response.sendRedirect("login.jsp?error=Username and password are required");
                return;
            }

            // Simple authentication (in production, use database + password hashing)
            // For demo: username = "admin", password = "admin123"
            if (authenticateUser(username, password)) {
                
                // Create session
                HttpSession session = request.getSession(true);
                session.setAttribute("user", username);
                session.setAttribute("loginTime", System.currentTimeMillis());
                session.setMaxInactiveInterval(30 * 60); // 30 minutes timeout
                
                System.out.println("✅ User logged in: " + username);
                response.sendRedirect("dashboard");
                
            } else {
                System.out.println("❌ Login failed for: " + username);
                response.sendRedirect("login.jsp?error=Invalid username or password");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=An error occurred during login");
        }
    }

    /**
     * Authenticate user credentials
     * In production, implement proper database lookup with password hashing (BCrypt, etc.)
     */
    private boolean authenticateUser(String username, String password) {
        // Simple in-memory authentication for demo
        // TODO: Implement database authentication with hashed passwords
        
        return username.equals("admin") && password.equals("admin123");
    }
}
