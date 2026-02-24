@echo off
setlocal

rem Configurar rutas de Java y Ant
set "JAVA_HOME=%~dp0tools\jdk8"
set "ANT_HOME=%~dp0tools\ant"
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo.
echo ==========================================
echo 1. COMPILANDO PROYECTO (Ant Jar)
echo ==========================================
call "%ANT_HOME%\bin\ant" jar
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERROR] La compilacion fallo.
    echo Revise el codigo fuente.
    pause
    exit /b %ERRORLEVEL%
)

echo.
echo ==========================================
echo 2. INICIANDO SOLTEC POS (Modo Dev)
echo ==========================================
echo Usando JAR: build\jar\SoltecPOS.jar
echo.

rem Ejecutar la aplicacion usando el JAR recien compilado
if exist "%~dp0jre\bin\java.exe" (
    "%~dp0jre\bin\java.exe" -cp "build\jar\SoltecPOS.jar;lib\*" com.openbravo.pos.forms.StartPOS "%~dp0soltec.properties"
) else (
    java -cp "build\jar\SoltecPOS.jar;lib\*" com.openbravo.pos.forms.StartPOS "%~dp0soltec.properties"
)

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERROR] La aplicacion se cerro con error %ERRORLEVEL%
    pause
)
