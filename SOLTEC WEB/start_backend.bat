@echo off
echo ============================================
echo   SOLTEC WEB - Starting Backend Server
echo ============================================
echo.

REM Try to find Maven
where mvn >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo [OK] Maven found
    cd /d "%~dp0backend"
    echo [..] Building and starting Spring Boot...
    echo [..] Server will be available at: http://localhost:8085
    echo.
    mvn spring-boot:run
) else (
    echo [!!] Maven not found in PATH
    echo.
    echo Please install Maven or use the JAR directly:
    echo   1. Install Maven: https://maven.apache.org/download.cgi
    echo   2. Or build with: mvnw spring-boot:run
    echo.
    pause
)
