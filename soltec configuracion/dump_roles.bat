@echo off
set JAVA_HOME=c:\Users\aux1\Documents\loquedejolatormenta\tools\jdk8
set PATH=%JAVA_HOME%\bin;%PATH%

echo Compiling DumpRoles...
"%JAVA_HOME%\bin\javac" -cp "lib/*;smartpos-3.5.9/lib/*;src/main/java" -d build src/main/java/com/openbravo/pos/admin/DumpRoles.java

if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)

echo Running DumpRoles...
"%JAVA_HOME%\bin\java" -cp "build;lib/*;smartpos-3.5.9/lib/*" com.openbravo.pos.admin.DumpRoles > roles_dump.txt

echo Done. Check roles_dump.txt
