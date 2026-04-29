# Food Delivery App - Build Script

## Quick Start

The `build.sh` script automates building and deploying the application to Tomcat.

### Basic Usage

```bash
# Build only (generate WAR file)
./build.sh build

# Build and deploy to Tomcat
./build.sh deploy

# Auto-detect Tomcat and deploy
./build.sh deploy-auto

# Deploy to specific Tomcat location
./build.sh deploy-path /opt/tomcat/webapps

# Clean build artifacts
./build.sh clean

# Show help
./build.sh help
```

## Command Details

### 1. `./build.sh build`
- Builds the project using Maven
- Generates `WebApp/target/fooddelivery.war`
- Shows build summary with file location and size

### 2. `./build.sh deploy`
- Builds the project
- Prompts you for Tomcat location
- Deploys WAR to the specified location
- Shows deployment summary

### 3. `./build.sh deploy-auto`
- Builds the project
- Automatically searches for Tomcat in common locations:
  - `/opt/tomcat`
  - `/usr/local/tomcat`
  - `/usr/local/opt/tomcat`
  - `$HOME/tomcat`
  - `/opt/homebrew/Cellar/tomcat`
- Auto-deploys if found

### 4. `./build.sh deploy-path /path/to/tomcat/webapps`
- Builds the project
- Deploys to your specified Tomcat path
- Example: `./build.sh deploy-path ~/tomcat/webapps`

### 5. `./build.sh clean`
- Removes all build artifacts
- Cleans Maven cache

## Features

✅ **Colorized Output** - Easy to read build status
✅ **Error Handling** - Exits on failures with clear messages
✅ **Auto-Detection** - Finds Tomcat automatically
✅ **Backups** - Backs up existing deployments
✅ **Environment Variables** - Supports custom DB configuration
✅ **Help & Documentation** - Built-in help messages

## Environment Variables

Optional environment variables for customization:

```bash
# Set MySQL connection details
export FOODDB_URL="jdbc:mysql://localhost:3306/fooddb"
export FOODDB_USER="root"
export FOODDB_PASSWORD="your_password"

# Then run the script
./build.sh deploy
```

## Troubleshooting

### Script not executing
```bash
# Make it executable first
chmod +x build.sh
./build.sh help
```

### Maven not found
```bash
# Install Maven:
# macOS:
brew install maven

# Ubuntu/Debian:
sudo apt-get install maven

# Or download from: https://maven.apache.org/download.cgi
```

### Build fails with "Cannot find database"
```bash
# Ensure MySQL is running and database is created:
mysql -u root -p < WebApp/database/fooddb.sql

# Then rebuild:
./build.sh build
```

### Tomcat not detected
```bash
# Use explicit path:
./build.sh deploy-path /path/to/your/tomcat/webapps
```

## What the Script Does

1. **Validates** Maven installation
2. **Verifies** pom.xml exists
3. **Compiles** Java source code
4. **Runs** unit tests (or skips with `-DskipTests`)
5. **Packages** everything into WAR file
6. **Verifies** WAR was created successfully
7. **(Optional) Deploys** to Tomcat
8. **Shows** summary with next steps

## Output Example

```
========================================
Building Food Delivery App
========================================

ℹ Project Directory: /Users/kapilpal/sahasra/fooddeliveryApp
ℹ Building from: /Users/kapilpal/sahasra/fooddeliveryApp/WebApp
ℹ Running: mvn clean package -DskipTests

[INFO] BUILD SUCCESS

✓ Build completed successfully!

✓ WAR file generated: .../target/fooddelivery.war (4.4M)

========================================
Build Summary
========================================

Project:          Food Delivery App
Version:          1.0.0
Build Status:     SUCCESS
WAR File:         fooddelivery.war
Location:         /Users/kapilpal/sahasra/fooddeliveryApp/WebApp/target/fooddelivery.war
Size:             4.4M

Next Steps:
1. Deploy WAR to Tomcat (copy to webapps/ folder)
2. Start Tomcat server
3. Access: http://localhost:8080/fooddelivery
4. Login with: admin / admin123
```

## Deployment Workflow

```
./build.sh deploy
    ↓
Build project with Maven
    ↓
Ask for Tomcat location (or auto-detect)
    ↓
Back up existing deployment
    ↓
Copy fooddelivery.war to Tomcat/webapps/
    ↓
Display deployment summary
    ↓
Show next steps
```

## After Deployment

1. **Start Tomcat:**
   ```bash
   /path/to/tomcat/bin/startup.sh
   ```

2. **Access Application:**
   - URL: `http://localhost:8080/fooddelivery`
   - Login: `admin` / `admin123`

3. **View Logs:**
   ```bash
   tail -f /path/to/tomcat/logs/catalina.out
   ```

4. **Stop Tomcat:**
   ```bash
   /path/to/tomcat/bin/shutdown.sh
   ```

---

**Script Location:** `/Users/kapilpal/sahasra/fooddeliveryApp/build.sh`

**Created:** April 29, 2026
