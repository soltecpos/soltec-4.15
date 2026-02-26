@echo off
REM ==========================================
REM Script de arranque SOLTEC WEB (Produccion)
REM ==========================================
echo Iniciando Servidor SOLTEC WEB...

REM Busca la version de Java embebida o usa la del sistema
set JAVA_EXE=java
if exist "%~dp0jre\bin\java.exe" set JAVA_EXE="%~dp0jre\bin\java.exe"

REM Inicia el backend en el puerto 8085
echo Ruta del JAR: "%~dp0backend\target\soltec-web-1.0.0.jar"
start "SOLTEC WEB" %JAVA_EXE% -jar "%~dp0backend\target\soltec-web-1.0.0.jar"

echo El servidor se esta iniciando.
echo Para acceder a la plataforma web, abre tu navegador y entra a:
echo http://localhost:8085
echo.
echo Puedes presionar cualquier tecla para cerrar esta ventana.
pause > nul
