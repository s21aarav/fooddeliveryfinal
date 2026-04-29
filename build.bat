@echo off
REM ============================================================================
REM Food Delivery App - Build & Deployment Script (Windows)
REM ============================================================================
REM This script automates building the WAR file and optionally deploys to Tomcat
REM ============================================================================

setlocal enabledelayedexpansion

REM Configuration
set PROJECT_DIR=%~dp0
set WEBAPP_DIR=%PROJECT_DIR%WebApp
set TARGET_DIR=%WEBAPP_DIR%\target
set WAR_FILE=%TARGET_DIR%\fooddelivery.war
set TOMCAT_WEBAPPS=

REM ============================================================================
REM Main Script
REM ============================================================================

:main
cls
echo.
echo ========================================
echo Food Delivery App - Build Script (Windows)
echo ========================================
echo.

if "%1"=="" (
    call :show_help
    exit /b 0
)

if "%1"=="build" (
    call :build_war
    exit /b 0
)

if "%1"=="deploy" (
    call :auto_deploy
    exit /b 0
)

if "%1"=="deploy-path" (
    if "%2"=="" (
        echo [ERROR] Please provide Tomcat path
        echo Usage: build.bat deploy-path "C:\path\to\tomcat"
        exit /b 1
    )
    call :deploy_to_path "%2"
    exit /b 0
)

if "%1"=="clean" (
    call :clean_build
    exit /b 0
)

if "%1"=="help" (
    call :show_help
    exit /b 0
)

echo [ERROR] Unknown command: %1
call :show_help
exit /b 1

REM ============================================================================
REM Functions
REM ============================================================================

:build_war
echo.
echo [INFO] Building Food Delivery App...
echo [INFO] Running: mvn clean package -DskipTests
echo.

cd /d "%WEBAPP_DIR%"
call mvn clean package -DskipTests

if errorlevel 1 (
    echo.
    echo [ERROR] Build failed!
    exit /b 1
)

echo.
echo ========================================
echo [SUCCESS] Build completed successfully!
echo ========================================
echo.
echo WAR File:  %WAR_FILE%
echo.
echo Next Steps:
echo 1. Copy WAR to Tomcat: copy "%WAR_FILE%" "C:\path\to\tomcat\webapps\"
echo 2. Start Tomcat: C:\path\to\tomcat\bin\startup.bat
echo 3. Access: http://localhost:8080/fooddelivery
echo 4. Login: admin / admin123
echo.
goto :eof

:auto_deploy
echo.
echo [INFO] Auto-detecting Tomcat installation...
echo.

REM Check common Tomcat locations
if exist "C:\tomcat\bin\startup.bat" (
    set TOMCAT_PATH=C:\tomcat
    goto :do_deploy
) else if exist "C:\Program Files\tomcat\bin\startup.bat" (
    set TOMCAT_PATH=C:\Program Files\tomcat
    goto :do_deploy
) else if exist "C:\Program Files (x86)\tomcat\bin\startup.bat" (
    set TOMCAT_PATH=C:\Program Files (x86)\tomcat
    goto :do_deploy
) else (
    echo [ERROR] Tomcat not found in common locations
    echo Provide path manually: build.bat deploy-path "C:\path\to\tomcat"
    exit /b 1
)

:do_deploy
echo [INFO] Found Tomcat at: %TOMCAT_PATH%
call :build_war
if errorlevel 1 exit /b 1
call :deploy_to_path "%TOMCAT_PATH%"
exit /b 0

:deploy_to_path
setlocal enabledelayedexpansion
set TOMCAT=%~1

echo.
echo [INFO] Deploying to Tomcat at: %TOMCAT%
echo.

if not exist "%TOMCAT%\bin\shutdown.bat" (
    echo [ERROR] Tomcat not found at: %TOMCAT%
    exit /b 1
)

echo [INFO] Stopping Tomcat...
call "%TOMCAT%\bin\shutdown.bat" > nul 2>&1
timeout /t 2 /nobreak > nul

echo [INFO] Removing old deployment...
if exist "%TOMCAT%\webapps\fooddelivery" (
    rmdir /s /q "%TOMCAT%\webapps\fooddelivery" > nul 2>&1
)
if exist "%TOMCAT%\webapps\fooddelivery.war" (
    del "%TOMCAT%\webapps\fooddelivery.war" > nul 2>&1
)

echo [INFO] Copying new WAR...
copy "%WAR_FILE%" "%TOMCAT%\webapps\" > nul
if errorlevel 1 (
    echo [ERROR] Failed to copy WAR file
    exit /b 1
)

echo [INFO] Starting Tomcat...
call "%TOMCAT%\bin\startup.bat" > nul 2>&1
timeout /t 3 /nobreak > nul

echo.
echo ========================================
echo [SUCCESS] Deployment completed!
echo ========================================
echo.
echo Access: http://localhost:8080/fooddelivery
echo Login: admin / admin123
echo.
endlocal
exit /b 0

:clean_build
echo [INFO] Cleaning build artifacts...
cd /d "%WEBAPP_DIR%"
call mvn clean
echo [SUCCESS] Clean completed!
exit /b 0

:show_help
echo.
echo Usage: build.bat [COMMAND] [OPTIONS]
echo.
echo Commands:
echo   build                    Build WAR file (mvn clean package)
echo   deploy                   Build and deploy to auto-detected Tomcat
echo   deploy-path PATH         Build and deploy to specified Tomcat path
echo   clean                    Clean build artifacts
echo   help                     Show this help message
echo.
echo Examples:
echo   build.bat build
echo   build.bat deploy
echo   build.bat deploy-path "C:\apache-tomcat-11.0.21"
echo.
exit /b 0
