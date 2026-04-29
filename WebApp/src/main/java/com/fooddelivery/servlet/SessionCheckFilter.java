package com.fooddelivery.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * SessionCheckFilter - Validates user session for protected pages
 * Automatically checks authentication before allowing access to protected URLs
 */
@WebFilter({"/viewOrders", "/premiumOrders", "/dashboard", "/deleteOrder", "/addOrder"})
public class SessionCheckFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Get session without creating new one
        HttpSession session = httpRequest.getSession(false);

        // Check if session exists and user is logged in
        if (session == null || session.getAttribute("user") == null) {
            System.out.println("⚠️ Unauthorized access attempt to: " + httpRequest.getRequestURI());
            
            // Redirect to login page
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp?error=Please login first");
            return;
        }

        // Check session timeout
        long lastAccessTime = (Long) session.getAttribute("loginTime");
        long currentTime = System.currentTimeMillis();
        long sessionTimeout = 30 * 60 * 1000; // 30 minutes

        if (currentTime - lastAccessTime > sessionTimeout) {
            session.invalidate();
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp?error=Session expired");
            return;
        }

        // Update last access time
        session.setAttribute("loginTime", currentTime);

        // User is authenticated, continue with request
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
