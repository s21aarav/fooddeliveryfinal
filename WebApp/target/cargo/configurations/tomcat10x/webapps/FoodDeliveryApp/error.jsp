<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error - Food Delivery Management</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <%
        // Check if user is logged in
        // Note: 'session' is an implicit object in JSP, no need to declare it
        String username = null;
        
        if (session != null) {
            username = (String) session.getAttribute("user");
        }
    %>

    <!-- Navbar -->
    <div class="navbar">
        <h1>🍔 Food Delivery Order Management System</h1>
        <div class="nav-links">
            <a href="index.jsp">➕ Add Order</a>
            <a href="viewOrders">📋 View All Orders</a>
            <a href="premiumOrders">💎 Premium Orders</a>
            <a href="dashboard">📊 Dashboard</a>
            <% if (username != null) { %>
                <span style="color: white; margin: 0 1rem;">👤 <%= username %></span>
                <a href="logout" style="background-color: #e74c3c; padding: 0.5rem 1rem; border-radius: 5px;">🚪 Logout</a>
            <% } else { %>
                <a href="login.jsp">🔐 Login</a>
            <% } %>
        </div>
    </div>

    <!-- Main Content -->
    <div class="container">
        <div class="card" style="text-align: center; padding: 3rem;">
            <div style="font-size: 4rem; margin-bottom: 1rem;">⚠️</div>
            <h2 class="card-title">Oops! An Error Occurred</h2>
            <p style="color: #7f8c8d; font-size: 1.1rem; margin-bottom: 2rem;">
                <%= request.getParameter("message") != null ? request.getParameter("message") : "Something went wrong. Please try again." %>
            </p>
            <a href="index.jsp" class="btn btn-primary" style="font-size: 1.1rem; padding: 1rem 2rem;">
                🏠 Go Back to Home
            </a>
        </div>
    </div>

    <!-- Footer -->
    <footer>
        <p>&copy; 2024 Food Delivery Order Management System | All Rights Reserved</p>
        <p>Built with Java Servlets, JSP, MySQL & Bootstrap</p>
    </footer>
</body>
</html>
