@echo off
set JAVA_HOME=c:\Users\aux1\Documents\loquedejolatormenta\tools\jdk8
set PATH=%JAVA_HOME%\bin;%PATH%
set ANT_HOME=c:\Users\aux1\Documents\loquedejolatormenta\tools\ant

echo Running Ant Clean...
call "%ANT_HOME%\bin\ant" clean

echo Running Ant Jar...
call "%ANT_HOME%\bin\ant" jar

echo Copying compiled JAR to root directory...
copy /Y build\jar\SoltecPOS.jar SoltecPOS.jar

echo Done.
pause
