# 🪟 Windows Setup Guide - Food Delivery App

> Complete step-by-step guide for Windows users (10/11)

---

## ✅ Prerequisites for Windows

### 1. Java Development Kit (JDK) 8+

**Check if installed:**
```cmd
java -version
```

Should show: `java version "1.8.0"` or higher

**If not installed:**
- Download: https://www.oracle.com/java/technologies/downloads/
- Choose: **Windows x64 Installer** for your system
- Install with default settings
- **Restart Command Prompt** after installation

### 2. Apache Maven 3.6+

**Check if installed:**
```cmd
mvn -version
```

**If not installed:**
- Download: https://maven.apache.org/download.cgi
- Choose: **Binary zip archive** (apache-maven-3.9.x-bin.zip)
- Extract to: `C:\maven` (or any location without spaces)
- Add to PATH:
  1. Right-click **This PC** → **Properties**
  2. Click **Advanced system settings**
  3. Click **Environment Variables**
  4. Under "System variables", click **New**
     - Variable name: `M2_HOME`
     - Variable value: `C:\maven\apache-maven-3.9.x` (your Maven location)
  5. Click **New** again
     - Variable name: `PATH`
     - Add to end: `;C:\maven\apache-maven-3.9.x\bin`
  6. Click **OK** and **restart Command Prompt**
- Verify: Open new Command Prompt and type `mvn -version`

### 3. MySQL Server 5.7+ (Community Edition)

**Check if installed:**
```cmd
mysql --version
```

**If not installed:**
- Download: https://dev.mysql.com/downloads/mysql/
- Choose: **MySQL Community Server** → **Windows (x86, 64-bit)**
- Run installer with default settings
- During setup:
  - **Config Type**: Development Machine
  - **TCP Port**: 3306 (default)
  - **Root password**: Set a password you remember! (or leave blank for no password)
- Finish installation
- **MySQL will run as a Windows service** (auto-start)

**Verify:**
```cmd
mysql -u root
```

If it connects, MySQL is running. Type `exit` to quit.

### 4. Apache Tomcat 9.x / 10.x / 11.x

**Download:**
- Visit: https://tomcat.apache.org/download-11.cgi
- Choose: **Core** → **Binary Distributions** → **zip** format
- Extract to: `C:\tomcat` (or `C:\Program Files\tomcat`)

**Verify:**
- Check if `C:\tomcat\bin\startup.bat` exists

---

## 🚀 Quick Setup (3 Steps)

### Step 1: Create Database

**Open Command Prompt** and run:

```cmd
REM Navigate to project folder
cd C:\Users\YourUsername\Desktop\fooddeliveryApp

REM Create database (if MySQL has no password):
mysql -u root < WebApp\database\fooddb.sql

REM OR if MySQL has a password:
mysql -u root -p < WebApp\database\fooddb.sql
REM (It will ask for your MySQL password)
```

**Verify it worked:**
```cmd
mysql -u root -e "USE fooddb; SELECT COUNT(*) as orders FROM orders;"
```

Should show: `5 orders`

### Step 2: Build the Application

In the **same Command Prompt**, run:

```cmd
REM Navigate to project root if not already there
cd C:\Users\YourUsername\Desktop\fooddeliveryApp

REM Run build script
build.bat build
```

**Expected output:**
```
...
[INFO] Building war: ...\target\fooddelivery.war
[INFO] BUILD SUCCESS
[SUCCESS] Build completed successfully!
```

**If it fails:**
- Check Java is installed: `java -version`
- Check Maven is installed: `mvn -version`
- Check you're in the correct folder

### Step 3: Deploy to Tomcat

**Option A: Automatic (Easiest)**

```cmd
build.bat deploy
```

This will:
- ✓ Find Tomcat automatically
- ✓ Stop Tomcat
- ✓ Copy WAR file
- ✓ Start Tomcat
- ✓ Wait 3 seconds for deployment

**Option B: Manual**

```cmd
REM 1. Copy WAR file
copy WebApp\target\fooddelivery.war C:\tomcat\webapps\

REM 2. Start Tomcat (opens new window)
C:\tomcat\bin\startup.bat

REM 3. Wait 3-5 seconds for deployment
timeout /t 5

REM 4. Open browser
start http://localhost:8080/fooddelivery
```

---

## 🔐 Database Password Setup (Important!)

### If MySQL has NO password (default):
Nothing to do! Application auto-detects.

### If MySQL HAS a password:

**Before starting Tomcat, set environment variable:**

**Option 1: Command Prompt (temporary - only for this session)**
```cmd
set FOODDB_PASSWORD=your_mysql_root_password
C:\tomcat\bin\startup.bat
```

**Option 2: Permanent (Windows Environment Variables)**
1. Right-click **This PC** → **Properties**
2. Click **Advanced system settings**
3. Click **Environment Variables** → **New** (under System variables)
   - Variable name: `FOODDB_PASSWORD`
   - Variable value: `your_mysql_root_password`
4. Click **OK**
5. **Restart Command Prompt** and start Tomcat

---

## 🎮 Access the Application

1. **Open browser**: http://localhost:8080/fooddelivery
2. **Login**:
   - Username: `admin`
   - Password: `admin123`
3. **Use features**:
   - ➕ Add Order
   - 📋 View All Orders
   - 💎 Premium Orders
   - 📊 Dashboard

---

## 🛑 Stop Tomcat

**Option 1: Graceful Shutdown (Recommended)**
```cmd
C:\tomcat\bin\shutdown.bat
```

**Option 2: Force Quit**
```cmd
taskkill /IM java.exe /F
```

---

## 🐛 Troubleshooting on Windows

### Problem: "mvn is not recognized"
```
❌ Cause: Maven not in PATH

✅ Solution:
1. Verify Maven location: dir C:\maven
2. Add to PATH (see Prerequisites step 2)
3. Restart Command Prompt
4. Try again: mvn -version
```

### Problem: "Can't connect to MySQL"
```
❌ Cause: MySQL not running or wrong password

✅ Solution:
1. Check MySQL service:
   Services.msc (press Win+R, type it)
   Look for "MySQL80" or similar → should show "Running"

2. If not running, start it:
   net start MySQL80
   (replace MySQL80 with your version)

3. If password wrong, set it:
   set FOODDB_PASSWORD=correct_password
   Then restart Tomcat
```

### Problem: "Tomcat won't start"
```
❌ Cause: Port 8080 already in use

✅ Solution:
1. Find what's using port 8080:
   netstat -ano | findstr :8080

2. Kill the process:
   taskkill /PID <process_id> /F

3. Or use different port (advanced - edit catalina.properties)
```

### Problem: "HTTP 404 - Not Found"
```
❌ Cause: Application not deployed

✅ Solution:
1. Check Tomcat logs:
   type C:\tomcat\logs\catalina.2026-04-29.log

2. Check WAR file exists:
   dir C:\tomcat\webapps\fooddelivery.war

3. Restart Tomcat:
   C:\tomcat\bin\shutdown.bat
   (wait 2 seconds)
   C:\tomcat\bin\startup.bat
   (wait 5 seconds)
```

### Problem: "Login fails with any password"
```
❌ Cause: Database not created properly

✅ Solution:
1. Verify database exists:
   mysql -u root -e "SHOW DATABASES;"
   (Should show: fooddb)

2. Check users table:
   mysql -u root -e "USE fooddb; SELECT * FROM users;"
   (Should show admin user)

3. If missing, recreate database:
   mysql -u root -e "DROP DATABASE fooddb;"
   mysql -u root < WebApp\database\fooddb.sql
```

---

## 📝 Build Script Commands (Windows)

Once you have the project, use `build.bat`:

```cmd
REM Build only (creates WAR file)
build.bat build

REM Build and deploy to auto-found Tomcat
build.bat deploy

REM Build and deploy to custom Tomcat location
build.bat deploy-path "C:\Program Files\Apache Tomcat 11.0.21"

REM Clean previous build
build.bat clean

REM Show help
build.bat help
```

---

## 📋 File Paths for Windows

| Component | Windows Path |
|-----------|--------------|
| **Project Root** | `C:\Users\YourName\Desktop\fooddeliveryApp` |
| **WebApp Folder** | `C:\Users\YourName\Desktop\fooddeliveryApp\WebApp` |
| **Database Schema** | `C:\Users\YourName\Desktop\fooddeliveryApp\WebApp\database\fooddb.sql` |
| **WAR File** | `C:\Users\YourName\Desktop\fooddeliveryApp\WebApp\target\fooddelivery.war` |
| **Tomcat Location** | `C:\tomcat` (or `C:\Program Files\tomcat`) |
| **Tomcat Webapps** | `C:\tomcat\webapps` |
| **Tomcat Logs** | `C:\tomcat\logs\catalina.2026-04-29.log` |

---

## ✅ Verification Checklist

- [ ] Java installed: `java -version` works
- [ ] Maven installed: `mvn -version` works
- [ ] MySQL running: `mysql -u root` connects
- [ ] Database created: `mysql -u root -e "USE fooddb; SELECT 1;"`
- [ ] Tomcat extracted: `dir C:\tomcat\bin\startup.bat` works
- [ ] WAR built: `dir WebApp\target\fooddelivery.war`
- [ ] Application accessible: http://localhost:8080/fooddelivery loads
- [ ] Login works: admin / admin123

---

## 🎯 Next Steps

1. **Follow Step 1-3 above** for basic setup
2. **Use build.bat deploy** for automatic deployment
3. **Access at** http://localhost:8080/fooddelivery
4. **Login with** admin / admin123
5. **Read** [SETUP_FOR_TEACHER.md](SETUP_FOR_TEACHER.md) for more options

---

**Windows setup complete! Happy coding! 🚀**
