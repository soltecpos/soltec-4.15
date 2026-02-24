@echo off
set JAVA_HOME=c:\Users\aux1\Documents\loquedejolatormenta\tools\jdk8
set PATH=%JAVA_HOME%\bin;%PATH%

echo Updating Menu...
"%JAVA_HOME%\bin\java" -cp "build;lib/*;smartpos-3.5.9/lib/*" com.openbravo.pos.admin.UpdateMenu

echo Creating GrantAllPermissions...
"%JAVA_HOME%\bin\javac" -cp "lib/*;smartpos-3.5.9/lib/*;src/main/java" -d build src/main/java/com/openbravo/pos/admin/GrantAllPermissions.java

echo Granting Permissions...
"%JAVA_HOME%\bin\java" -cp "build;lib/*;smartpos-3.5.9/lib/*" com.openbravo.pos.admin.GrantAllPermissions

echo Done.
