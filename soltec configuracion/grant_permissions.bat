@echo off
set JAVA_HOME=c:\Users\aux1\Documents\loquedejolatormenta\tools\jdk8
set PATH=%JAVA_HOME%\bin;%PATH%
set CP=build\classes;build\jar\SoltecPOS.jar;lib\*;
echo Granting all permissions...
java -cp "%CP%" com.openbravo.pos.admin.GrantAllPermissions
pause
