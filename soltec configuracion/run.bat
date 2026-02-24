@echo off
setlocal

rem Ensure we are running from the script's directory
cd /d "%~dp0"

rem Use the bundled JRE if available
if exist "jre" (
    set "JAVA_HOME=%~dp0jre"
    set "PATH=%~dp0jre\bin;%PATH%"
)

rem Launch the application using wildcard classpath
if exist "jre\bin\javaw.exe" (
    start "" "jre\bin\javaw.exe" -cp "SoltecPOS.jar;lib\*" com.openbravo.pos.forms.StartPOS "%~dp0soltec.properties"
) else (
    start "" javaw -cp "SoltecPOS.jar;lib\*" com.openbravo.pos.forms.StartPOS "%~dp0soltec.properties"
)
