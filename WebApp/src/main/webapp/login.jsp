<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Food Delivery Management</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
        }

        .login-container {
            background: white;
            padding: 3rem 2rem;
            border-radius: 15px;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.3);
            width: 100%;
            max-width: 400px;
            animation: slideIn 0.5s ease;
        }

        @keyframes slideIn {
            from {
                opacity: 0;
                transform: translateY(-30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .login-header {
            text-align: center;
            margin-bottom: 2rem;
        }

        .login-header h1 {
            color: #2c3e50;
            margin: 0 0 0.5rem 0;
            font-size: 2rem;
        }

        .login-header p {
            color: #7f8c8d;
            margin: 0;
        }

        .logo {
            font-size: 3rem;
            margin-bottom: 1rem;
        }

        .form-group {
            margin-bottom: 1.5rem;
        }

        label {
            display: block;
            margin-bottom: 0.5rem;
            color: #2c3e50;
            font-weight: 600;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 0.75rem;
            border: 2px solid #ecf0f1;
            border-radius: 8px;
            font-size: 1rem;
            transition: all 0.3s ease;
            box-sizing: border-box;
        }

        input[type="text"]:focus,
        input[type="password"]:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
        }

        .login-btn {
            width: 100%;
            padding: 0.85rem;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 1.1rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            margin-top: 1rem;
        }

        .login-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 20px rgba(102, 126, 234, 0.4);
        }

        .login-btn:active {
            transform: translateY(0);
        }

        .alert {
            padding: 1rem;
            margin-bottom: 1.5rem;
            border-radius: 8px;
            border-left: 4px solid;
        }

        .alert-error {
            background-color: #f8d7da;
            border-color: #dc3545;
            color: #721c24;
        }

        .alert-success {
            background-color: #d4edda;
            border-color: #28a745;
            color: #155724;
        }

        .alert-info {
            background-color: #d1ecf1;
            border-color: #17a2b8;
            color: #0c5460;
        }

        .demo-credentials {
            background: #e3f2fd;
            border: 1px solid #90caf9;
            padding: 1rem;
            border-radius: 8px;
            margin-top: 2rem;
            color: #1565c0;
            font-size: 0.9rem;
            line-height: 1.6;
        }

        .demo-credentials strong {
            color: #0d47a1;
        }

        .remember-me {
            display: flex;
            align-items: center;
            margin-bottom: 1rem;
            color: #2c3e50;
        }

        .remember-me input {
            margin-right: 0.5rem;
            cursor: pointer;
        }

        .footer-text {
            text-align: center;
            color: #95a5a6;
            font-size: 0.85rem;
            margin-top: 2rem;
        }

        .security-badge {
            text-align: center;
            color: #27ae60;
            margin-top: 1.5rem;
            font-size: 0.9rem;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <!-- Header -->
        <div class="login-header">
            <div class="logo">🍔</div>
            <h1>Food Delivery</h1>
            <p>Order Management System</p>
        </div>

        <!-- Error/Success Messages -->
        <%
            String error = request.getParameter("error");
            String message = request.getParameter("message");
            
            if (error != null && !error.isEmpty()) {
        %>
            <div class="alert alert-error">
                ❌ <%= error %>
            </div>
        <%
            }
            if (message != null && !message.isEmpty()) {
        %>
            <div class="alert alert-success">
                ✅ <%= message %>
            </div>
        <%
            }
        %>

        <!-- Login Form -->
        <form method="post" action="login" id="loginForm">
            <!-- Username Field -->
            <div class="form-group">
                <label for="username">👤 Username</label>
                <input 
                    type="text" 
                    id="username" 
                    name="username" 
                    placeholder="Enter your username"
                    required
                    autofocus>
            </div>

            <!-- Password Field -->
            <div class="form-group">
                <label for="password">🔑 Password</label>
                <input 
                    type="password" 
                    id="password" 
                    name="password" 
                    placeholder="Enter your password"
                    required>
            </div>

            <!-- Remember Me -->
            <div class="remember-me">
                <input type="checkbox" id="remember" name="remember">
                <label for="remember" style="margin: 0;">Remember me</label>
            </div>

            <!-- Login Button -->
            <button type="submit" class="login-btn">🔐 Login</button>
        </form>

        <!-- Demo Credentials -->
        <div class="demo-credentials">
            <strong>📝 Demo Credentials:</strong><br>
            Username: <code>admin</code><br>
            Password: <code>admin123</code>
        </div>

        <!-- Security Badge -->
        <div class="security-badge">
            🔒 Secure Login | Session Protected
        </div>

        <!-- Footer -->
        <div class="footer-text">
            <p>&copy; 2024 Food Delivery Management System</p>
            <p>All Rights Reserved</p>
        </div>
    </div>

    <script>
        // Form validation
        document.getElementById('loginForm').addEventListener('submit', function(e) {
            const username = document.getElementById('username').value.trim();
            const password = document.getElementById('password').value.trim();

            if (!username) {
                alert('❌ Please enter username');
                e.preventDefault();
                return false;
            }

            if (!password) {
                alert('❌ Please enter password');
                e.preventDefault();
                return false;
            }

            if (username.length < 3) {
                alert('❌ Username must be at least 3 characters');
                e.preventDefault();
                return false;
            }

            if (password.length < 6) {
                alert('❌ Password must be at least 6 characters');
                e.preventDefault();
                return false;
            }

            return true;
        });

        // Focus effects
        document.getElementById('username').addEventListener('focus', function() {
            this.style.borderColor = '#667eea';
        });

        document.getElementById('password').addEventListener('focus', function() {
            this.style.borderColor = '#667eea';
        });
    </script>
</body>
</html>
