@echo off
echo ============================================
echo   SOLTEC WEB - Starting Frontend
echo ============================================
echo.
echo Opening frontend in default browser...
echo.
start "" "http://localhost:3000"
echo.
echo Starting simple HTTP server on port 3000...
cd /d "%~dp0frontend"

REM Try Python first
where python >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    python -m http.server 3000
    goto :eof
)

REM Try Python3
where python3 >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    python3 -m http.server 3000
    goto :eof
)

REM Try npx serve
where npx >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    npx -y serve -p 3000
    goto :eof
)

echo [!!] No HTTP server found. Install Python or Node.js
pause
