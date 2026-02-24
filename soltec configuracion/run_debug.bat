@echo off
setlocal

rem Ensure we are running from the script's directory
cd /d "%~dp0"

echo ---------------------------------------------------
echo Soltec POS Debug Launcher
echo ---------------------------------------------------

rem Use the bundled JRE if available
if exist "jre" (
    echo Using bundled JRE...
    set "JAVA_HOME=%~dp0jre"
    set "PATH=%~dp0jre\bin;%PATH%"
) else (
    echo Using system Java...
)

echo Java Version:
java -version
echo.

echo Launching Soltec POS...
echo Classpath: SoltecPOS.jar;lib\*
echo Main Class: com.openbravo.pos.forms.StartPOS
echo Log file: pos_console.log
echo.

rem Delete old log
if exist "pos_console.log" del /f "pos_console.log"

if exist "jre\bin\java.exe" (
    "jre\bin\java.exe" -cp "SoltecPOS.jar;lib\*" com.openbravo.pos.forms.StartPOS "%~dp0soltec.properties" > pos_console.log 2>&1
) else (
    java -cp "SoltecPOS.jar;lib\*" com.openbravo.pos.forms.StartPOS "%~dp0soltec.properties" > pos_console.log 2>&1
)

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ---------------------------------------------------
    echo ERROR: Application exited with code %ERRORLEVEL%
    echo See pos_console.log for details
    echo ---------------------------------------------------
)

echo.
echo --- El programa termino. Ver pos_console.log para mensajes de debug ---
pause
