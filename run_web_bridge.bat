@echo off
set JAVA_EXE="C:\Users\aux1\Documents\loquedejolatormenta\tools\jdk8\bin\java.exe"
set CP="web-bridge/bin;lib/mysql-connector-java-5.1.34.jar"
echo Iniciando Soltec Web Bridge...
echo Abra http://localhost:8080 en su navegador.
%JAVA_EXE% -cp %CP% WebBridge
pause
