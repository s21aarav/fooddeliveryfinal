# 🚀 Quick Setup Guide for Food Delivery App

> **TL;DR**: Follow these 3 simple steps to get the app running

---

## Step 1️⃣: Prerequisites Check

Before starting, make sure you have these installed:

### Java
```bash
java -version
# Should show Java 8 or higher
```
If not installed, download from: https://www.oracle.com/java/technologies/downloads/

### MySQL  
```bash
mysql -u root -e "SELECT 1;"
```
If not installed, download from: https://www.mysql.com/downloads/

**Note:** Remember your MySQL root password! If you don't know it:
- Use `mysql -u root` (if no password is set)
- Use `mysql -u root -p` (and enter password when prompted)

### Apache Tomcat
Download from: https://tomcat.apache.org/download-11.cgi

Choose: `Core` → Binary Distributions → `.tar.gz` (macOS/Linux) or `.zip` (Windows)

Extract to a location like:
- macOS/Linux: `/opt/tomcat` or `~/tomcat`
- Windows: `C:\tomcat` or `C:\Program Files\tomcat`

---

## Step 2️⃣: Deploy the Application

### A. Create Database

From the **project root directory**, run:

```bash
# If your MySQL has no password:
mysql -u root < WebApp/database/fooddb.sql

# If your MySQL has a password:
mysql -u root -p < WebApp/database/fooddb.sql
# Enter your password when prompted
```

**Verify it worked:**
```bash
mysql -u root -e "USE fooddb; SELECT COUNT(*) as orders FROM orders;"
# Should show: 5 orders
```

### B. Build the WAR File

**macOS/Linux:**
```bash
cd WebApp
./build.sh build
```

**Windows:**
```cmd
build.bat build
```

**Or use Maven directly:**
```bash
cd WebApp
mvn clean package
```

### C. Set MySQL Password (if needed)

If your MySQL root user **has a password**, set this environment variable FIRST:

```bash
# macOS/Linux:
export FOODDB_PASSWORD="your_mysql_root_password"

# Windows (Command Prompt):
set FOODDB_PASSWORD=your_mysql_root_password

# Windows (PowerShell):
$env:FOODDB_PASSWORD="your_mysql_root_password"
```

### D. Deploy to Tomcat

**macOS/Linux:**
```bash
# Copy WAR file
cp WebApp/target/fooddelivery.war /path/to/tomcat/webapps/

# Start Tomcat
/path/to/tomcat/bin/startup.sh

# Wait 3 seconds and access
sleep 3
open http://localhost:8080/fooddelivery
```

**Windows:**
```cmd
REM Copy WAR file
copy WebApp\target\fooddelivery.war C:\tomcat\webapps\

REM Start Tomcat
C:\tomcat\bin\startup.bat

REM Wait and access
timeout /t 3
start http://localhost:8080/fooddelivery
```

---

## Step 3️⃣: Access the Application

Open your browser and go to:

```
http://localhost:8080/fooddelivery
```

**Login with:**
- Username: `admin`
- Password: `admin123`

---

## 🎯 What You Can Do

Once logged in:

✅ **Add Orders** - Enter new food delivery orders  
✅ **View All Orders** - See complete order list with status  
✅ **Premium Orders** - Filter orders above ₹1000  
✅ **Dashboard** - View order statistics and revenue  
✅ **Delete Orders** - Remove orders from the system  

---

## 🐛 Troubleshooting

### "Can't connect to database"
```
❌ Solution: Check if MySQL is running
$ mysql -u root -e "SELECT 1;"

If it fails with "ERROR 2002", MySQL is not running.
Start MySQL first before starting Tomcat.
```

### "ERROR 1045: Access denied for user 'root'"
```
❌ Solution: Your MySQL root password is not set correctly

$ export FOODDB_PASSWORD="correct_password"  # macOS/Linux
$ set FOODDB_PASSWORD=correct_password        # Windows Command Prompt

Restart Tomcat after setting the password.
```

### "Application won't load (HTTP 404 or 500)"
```
❌ Solution 1: Check if Tomcat is running
$ curl http://localhost:8080/
# Should return HTML

❌ Solution 2: Check Tomcat logs
macOS/Linux: tail -50 /path/to/tomcat/logs/catalina.out
Windows: type C:\path\to\tomcat\logs\catalina.2026-04-29.log
```

### "Login always fails"
```
❌ Solution: Ensure database was created
$ mysql -u root -e "USE fooddb; SELECT * FROM users;"
# Should show admin user with password "admin123"
```

### "Port 8080 already in use" (Windows)
```
❌ Solution: Find and stop the process
$ netstat -ano | findstr :8080
$ taskkill /PID <process_id> /F
```

---

## 📋 Files Reference

```
fooddeliveryApp/
├── WebApp/target/fooddelivery.war    ← Deploy this file
├── WebApp/database/fooddb.sql        ← Run this to create database
├── README.md                         ← Full documentation
├── WINDOWS_SETUP.md                  ← Windows-specific guide
└── SETUP_FOR_TEACHER.md              ← This file
```

---

## ❓ Need Help?

1. **Check logs** for detailed error messages
2. **Verify MySQL** is running: `mysql -u root -e "SELECT 1;"`
3. **Verify Tomcat** is running: Visit `http://localhost:8080` in browser
4. **Check network** - Make sure port 8080 is not blocked

---

**That's it! Your Food Delivery App is ready to use** 🎉

For more details, see [README.md](README.md) or [WINDOWS_SETUP.md](WINDOWS_SETUP.md)
