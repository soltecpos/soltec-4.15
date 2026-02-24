@echo off
setlocal
cd /d "%~dp0"
echo Iniciando Soltec POS en modo Debug...
echo Los logs se mostraran en esta ventana.

rem Use the bundled JRE if available
if exist "jre" (
    set "JAVA_HOME=%~dp0jre"
    set "PATH=%~dp0jre\bin;%PATH%"
)

rem Launch with console output (java, not javaw)
java -cp "SoltecPOS.jar;lib\*" com.openbravo.pos.forms.StartPOS "%~dp0soltec.properties"
pause
