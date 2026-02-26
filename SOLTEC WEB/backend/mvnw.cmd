@REM Maven Wrapper for Windows
@REM Simplified version for SOLTEC WEB

@echo off
setlocal

set JAVA_HOME=C:\Users\aux1\.antigravity\extensions\redhat.java-1.12.0-win32-x64\jre\17.0.4.1-win32-x86_64
set PATH=%JAVA_HOME%\bin;%PATH%
set WRAPPER_JAR="%~dp0.mvn\wrapper\maven-wrapper.jar"

java -jar %WRAPPER_JAR% %*
