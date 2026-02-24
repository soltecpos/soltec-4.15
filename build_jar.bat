@echo off
set JAVA_HOME=c:\Users\aux1\Documents\loquedejolatormenta\tools\jdk8
set PATH=%JAVA_HOME%\bin;%PATH%
echo JAVA_HOME is %JAVA_HOME%
c:\Users\aux1\Documents\loquedejolatormenta\tools\ant\bin\ant.bat -f c:\Users\aux1\Documents\loquedejolatormenta\build.xml jar
