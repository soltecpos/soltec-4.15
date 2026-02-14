@echo off
echo Iniciando Soltec POS en modo Debug...
echo Los logs se mostraran en esta ventana.
set JAVA_HOME=tools\jdk8
set PATH=%JAVA_HOME%\bin;%PATH%
java -jar build\jar\SoltecPOS.jar
pause
