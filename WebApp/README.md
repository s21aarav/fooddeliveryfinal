# 🍔 Food Delivery Order Management System - Web Application

## 📋 Project Overview

A complete web-based **Food Delivery Order Management System** built with:
- **Backend**: Java Servlets (MVC Architecture)
- **Frontend**: JSP, HTML5, CSS3, JavaScript
- **Database**: MySQL 
- **Server**: Apache Tomcat
- **Design Pattern**: DAO (Data Access Object) Pattern
- **UI Framework**: Bootstrap/Custom CSS (Ant Design inspired)

---

## 📖 **QUICK START** 
### 👉 **See [COMPLETE_GUIDE.md](COMPLETE_GUIDE.md) for:**
- ✅ 5-minute quick start
- ✅ Complete setup instructions
- ✅ Deployment guide
- ✅ Architecture & design patterns
- ✅ Authentication system details
- ✅ Testing & troubleshooting
- ✅ Production enhancements
- ✅ FAQ & support

---

## 🎯 Architecture Flow

```
Browser (UI)
    ↓
Servlet (Controller)
    ↓
DAO (JDBC)
    ↓
MySQL Database
```

---

## 📁 Project Structure

```
FoodDeliveryApp/
├── WebApp/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/fooddelivery/
│   │   │   │       ├── model/
│   │   │   │       │   └── FoodOrder.java
│   │   │   │       ├── dao/
│   │   │   │       │   └── OrderDAO.java
│   │   │   │       ├── servlet/
│   │   │   │       │   ├── AddOrderServlet.java
│   │   │   │       │   ├── ViewOrdersServlet.java
│   │   │   │       │   ├── PremiumOrdersServlet.java
│   │   │   │       │   ├── DashboardServlet.java
│   │   │   │       │   ├── DeleteOrderServlet.java
│   │   │   │       │   ├── LoginServlet.java (NEW)
│   │   │   │       │   ├── LogoutServlet.java (NEW)
│   │   │   │       │   └── SessionCheckFilter.java (NEW)
│   │   │   │       └── util/
│   │   │   │           └── DBConnection.java
│   │   │   └── webapp/
│   │   │       ├── WEB-INF/
│   │   │       │   └── web.xml
│   │   │       ├── css/
│   │   │       │   └── style.css
│   │   │       ├── js/
│   │   │       │   └── main.js
│   │   │       ├── index.jsp
│   │   │       ├── viewOrders.jsp
│   │   │       ├── dashboard.jsp
│   │   │       ├── error.jsp
│   │   │       └── login.jsp (NEW)
│   ├── database/
│   │   └── fooddb.sql
│   └── lib/
│       └── (Place MySQL JDBC driver here)
└── README.md
```

---

## 🔧 Prerequisites

Before running the application, ensure you have:

1. **JDK 8 or higher** - [Download JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
2. **Apache Tomcat 9.x or 10.x** - [Download Tomcat](https://tomcat.apache.org/download-90.cgi)
3. **MySQL Server 5.7+** - [Download MySQL](https://dev.mysql.com/downloads/mysql/)
4. **MySQL Connector/J (JDBC Driver)** - [Download](https://dev.mysql.com/downloads/connector/j/)

---

## 🚀 Step-by-Step Setup Guide

### Step 1: Create Database

1. Open MySQL Command Line / MySQL Workbench
2. Run the SQL script from `database/fooddb.sql`:

```sql
-- Create Database
CREATE DATABASE IF NOT EXISTS fooddb;

USE fooddb;

-- Create orders table
CREATE TABLE IF NOT EXISTS orders (
    orderId VARCHAR(50) PRIMARY KEY,
    customerName VARCHAR(100) NOT NULL,
    restaurant VARCHAR(100) NOT NULL,
    amount DOUBLE NOT NULL,
    status VARCHAR(50) NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create indexes
CREATE INDEX idx_customer_name ON orders(customerName);
CREATE INDEX idx_restaurant ON orders(restaurant);
CREATE INDEX idx_status ON orders(status);
CREATE INDEX idx_amount ON orders(amount);
```

### Step 2: Download MySQL JDBC Driver

1. Download **MySQL Connector/J** from [MySQL Official Site](https://dev.mysql.com/downloads/connector/j/)
2. Extract the JAR file: `mysql-connector-java-8.0.x.jar`
3. Place it in: `WebApp/lib/mysql-connector-java-8.0.x.jar`

### Step 3: Configure Database Connection

Open `src/main/java/com/fooddelivery/util/DBConnection.java` and update:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/fooddb";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_mysql_password";  // Change this
```

### Step 4: Compile the Project

#### Using Command Line:

```bash
# Navigate to WebApp directory
cd WebApp

# Compile all Java files
javac -cp lib/mysql-connector-java-8.0.x.jar -d src/main/webapp/WEB-INF/classes ^
    src/main/java/com/fooddelivery/model/*.java ^
    src/main/java/com/fooddelivery/dao/*.java ^
    src/main/java/com/fooddelivery/servlet/*.java ^
    src/main/java/com/fooddelivery/util/*.java
```

#### Using Maven (Recommended):

```bash
mvn clean compile
mvn clean package
```

### Step 5: Create WAR File

Create the WAR (Web Archive) structure:

```
fooddelivery.war/
├── WEB-INF/
│   ├── classes/
│   │   └── com/fooddelivery/... (all compiled classes)
│   ├── lib/
│   │   └── mysql-connector-java-8.0.x.jar
│   └── web.xml
├── css/
│   └── style.css
├── js/
│   └── main.js
├── index.jsp
├── viewOrders.jsp
├── dashboard.jsp
└── error.jsp
```

### Step 6: Deploy to Tomcat

#### Method 1: Copy WAR file

1. Create WAR file: `fooddelivery.war`
2. Copy to: `TOMCAT_HOME/webapps/fooddelivery.war`
3. Tomcat will auto-extract it

#### Method 2: Copy entire folder

1. Copy the entire `WebApp` folder to: `TOMCAT_HOME/webapps/fooddelivery/`
2. Rename `src/main/webapp` contents to the root

#### Method 3: Using Tomcat Manager

1. Start Tomcat
2. Open: `http://localhost:8080/manager/html`
3. Upload WAR file or specify directory path

### Step 7: Start Apache Tomcat

**Windows:**
```bash
cd TOMCAT_HOME/bin
startup.bat
```

**Linux/Mac:**
```bash
cd TOMCAT_HOME/bin
./startup.sh
```

---

## 🌐 Access the Application

Once Tomcat starts, open your browser and navigate to:

```
http://localhost:8080/fooddelivery
```

### Default Pages:
- **Home**: `http://localhost:8080/fooddelivery/index.jsp`
- **View Orders**: `http://localhost:8080/fooddelivery/viewOrders`
- **Premium Orders**: `http://localhost:8080/fooddelivery/premiumOrders`
- **Dashboard**: `http://localhost:8080/fooddelivery/dashboard`

---

## ✨ Features Implemented

### 1. **Add Order** ➕
- HTML form with validation
- Order ID, Customer Name, Restaurant, Amount, Status
- Real-time form validation (JavaScript)
- Automatic form clearing after successful submission

### 2. **View All Orders** 📋
- Display all orders in a data table
- Order statistics (total orders, revenue, average order value)
- Delete order functionality
- Status badges with color coding

### 3. **Premium Orders** 💎
- Filter orders with amount > ₹1000
- Same table view as "View All Orders"
- Quick access from navbar

### 4. **Dashboard** 📊
- Real-time statistics cards
- Total orders count
- Total revenue calculation
- Average order value
- Premium orders count
- Quick action buttons

### 5. **Database Operations** 🗄️
- INSERT: Add new order
- SELECT: Retrieve all orders / premium orders
- UPDATE: Update order status (ready for enhancement)
- DELETE: Remove orders
- Aggregate functions: COUNT, SUM, AVG

### 6. **🔐 Authentication & Security System** (NEW!)
- **Login System** with beautiful UI (gradient background, form validation)
- **Session Management** - 30 minute inactivity timeout
- **Access Control** - SessionCheckFilter protects all pages
- **User Database** - MySQL users table with authentication
- **Logout Functionality** - Secure session termination
- **Username Display** - Shows logged-in user in navbar
- **Demo Credentials** - Admin account (admin/admin123)
- **HTTP-Only Cookies** - Prevents XSS attacks
- **Automatic Redirection** - Sends unauthenticated users to login page

**Secured Pages:**
- ✅ Add Order (/addOrder)
- ✅ View All Orders (/viewOrders)
- ✅ Premium Orders (/premiumOrders)
- ✅ Dashboard (/dashboard)
- ✅ Delete Order (/deleteOrder)

**Quick Start Login:**
```
Username: admin
Password: admin123
```

---

## 🎨 UI/UX Features

### Responsive Design
- Mobile-friendly Bootstrap grid layout
- Auto-adjusting forms and tables
- Touch-optimized buttons

### Beautiful UI
- Modern gradient buttons
- Status badges with color coding
- Alert messages (success, error, info)
- Icons using Unicode emoji
- Smooth transitions and hover effects
- Professional color scheme

### User Experience
- Form validation before submission
- Success/Error messages
- Confirmation dialogs for delete
- Auto-hiding alerts after 5 seconds
- Empty state handling

---

## 🔐 Security Features Implemented

1. **SQL Injection Prevention**: Using `PreparedStatement` in all queries
2. **Input Validation**: Both client-side (JavaScript) and server-side (Java)
3. **Error Handling**: Try-catch blocks and proper exception messages
4. **Database Connection Pooling Ready**: Can be enhanced with HikariCP

---

## 📊 Database Schema

### orders Table
```sql
Column Name      | Data Type | Constraints | Purpose
===============================================
orderId          | VARCHAR   | PRIMARY KEY | Unique order identifier
customerName     | VARCHAR   | NOT NULL    | Customer's name
restaurant       | VARCHAR   | NOT NULL    | Restaurant name
amount           | DOUBLE    | NOT NULL    | Order amount (₹)
status           | VARCHAR   | NOT NULL    | Order status
createdAt        | TIMESTAMP | DEFAULT NOW | Creation time
updatedAt        | TIMESTAMP | ON UPDATE   | Last update time
```

---

## 🧪 Testing the Application

### Test Data Insertion

Use the HTML form to add orders:
1. Order ID: `ORD001`
2. Customer Name: `Rajesh Kumar`
3. Restaurant: `Dominoes`
4. Amount: `850.50`
5. Status: `Delivered`

### Test Query Examples

```sql
-- Get all orders
SELECT * FROM orders;

-- Get premium orders
SELECT * FROM orders WHERE amount > 1000;

-- Calculate total revenue
SELECT SUM(amount) as total_revenue FROM orders;

-- Get average order value
SELECT AVG(amount) as avg_order_value FROM orders;

-- Count orders by status
SELECT status, COUNT(*) as count FROM orders GROUP BY status;
```

---

## 🐛 Troubleshooting

### Issue: "Connection refused" error
**Solution**: 
- Check if MySQL server is running
- Verify database name is `fooddb`
- Update `DBConnection.java` with correct credentials

### Issue: "Class not found: com.mysql.cj.jdbc.Driver"
**Solution**:
- Download MySQL Connector/J JAR
- Place in `WEB-INF/lib/` folder
- Restart Tomcat

### Issue: Tomcat can't find the WAR file
**Solution**:
- Ensure correct path: `TOMCAT_HOME/webapps/`
- Restart Tomcat
- Check `TOMCAT_HOME/logs/catalina.out`

### Issue: 404 Error when accessing pages
**Solution**:
- Verify context name matches URL (e.g., `http://localhost:8080/fooddelivery/`)
- Check web.xml is properly configured
- Verify JSP file names are correct

---

## 📚 Java Components Explained

### 1. **Model** (`FoodOrder.java`)
- POJO (Plain Old Java Object)
- Represents order entity
- Getters and setters for all fields

### 2. **DAO** (`OrderDAO.java`)
- Data Access Object pattern
- All database operations
- Uses JDBC with PreparedStatement
- Methods: addOrder, getAllOrders, getPremiumOrders, deleteOrder, etc.

### 3. **Servlets** (`*Servlet.java`)
- HTTP request handlers
- Process form submissions
- Redirect/Forward to JSP pages
- @WebServlet annotation for URL mapping

### 4. **Utility** (`DBConnection.java`)
- Database connection management
- Singleton pattern
- Connection pooling ready

### 5. **JSP Pages**
- Dynamic HTML generation
- JSTL and scriptlet integration
- Form handling
- Data display

---

## 🚀 Performance Optimization Tips

1. **Add Database Indexes** (already in SQL script)
2. **Use Connection Pooling** (HikariCP/C3P0)
3. **Cache Frequently Accessed Data**
4. **Implement Pagination** for large datasets
5. **Use CSS/JavaScript Minification**
6. **Enable Gzip Compression** in Tomcat

---

## � Documentation

All documentation has been consolidated into **[COMPLETE_GUIDE.md](COMPLETE_GUIDE.md)**.

### What's in COMPLETE_GUIDE.md:
- ✅ 5-minute quick start (5 simple steps)
- ✅ Installation & system requirements
- ✅ Project architecture & design patterns
- ✅ Authentication system details (login/logout/sessions)
- ✅ Features implemented (orders, dashboard, etc.)
- ✅ File structure & class descriptions
- ✅ Compilation guide (Windows & Linux)
- ✅ Deployment guide (step-by-step)
- ✅ Complete testing guide with scenarios
- ✅ Troubleshooting (common issues & solutions)
- ✅ Production enhancements (security, hashing, etc.)
- ✅ FAQ (frequently asked questions)

**Quick Start from COMPLETE_GUIDE.md:**
```
1. Database: mysql -u root -p < database/fooddb.sql
2. Compile: javac -cp lib/mysql-connector-java-8.0.33.jar ...
3. Deploy: Copy to TOMCAT_HOME/webapps/fooddelivery/
4. Start: TOMCAT_HOME/bin/startup.bat
5. Test: http://localhost:8080/fooddelivery/ (admin/admin123)
```

See [COMPLETE_GUIDE.md](COMPLETE_GUIDE.md) for detailed step-by-step instructions.


---

## 📈 Future Enhancements

- [x] User authentication and authorization ✅ DONE!
- [ ] Password hashing (BCrypt/Argon2)
- [ ] Two-factor authentication (2FA)
- [ ] Payment gateway integration
- [ ] Real-time order tracking with WebSocket
- [ ] Email notifications
- [ ] Order history and analytics
- [ ] Multi-language support (i18n)
- [ ] Dark mode UI
- [ ] Mobile app integration (REST API)
- [ ] Docker containerization
- [ ] Cloud deployment (AWS, Azure)

---

## 📞 Support & Contact

For issues or questions:
1. Check the Troubleshooting section
2. Review Tomcat logs: `TOMCAT_HOME/logs/`
3. Check browser console for JavaScript errors
4. Verify database connection in MySQL client
5. See [LOGIN_SYSTEM_GUIDE.md](LOGIN_SYSTEM_GUIDE.md) for authentication troubleshooting

---

## 📄 License

This project is created for educational purposes.

---

## 👨‍💻 Author

**Java Web Application Developer**
- Built with ❤️ using Java, Servlets, JSP & MySQL
- Ready for production deployment

---

## 🎓 Learning Resources

- **Java Servlets**: [Oracle Docs](https://docs.oracle.com/cd/E23545_01/webtier.1111/e13711/intro.htm)
- **JSP**: [Official JSP Specification](https://javaee.github.io/servlet-spec/)
- **JDBC**: [JDBC Tutorial](https://docs.oracle.com/javase/tutorial/jdbc/)
- **Tomcat**: [Tomcat Documentation](https://tomcat.apache.org/tomcat-10.0-doc/)
- **MySQL**: [MySQL Documentation](https://dev.mysql.com/doc/)

---

**Happy Coding! 🚀** 🍔
