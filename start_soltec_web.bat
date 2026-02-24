@echo off
set JAVA_HOME=C:\Users\aux1\.antigravity\extensions\redhat.java-1.12.0-win32-x64\jre\17.0.4.1-win32-x86_64
set PATH=%JAVA_HOME%\bin;%PATH%
cd /d "c:\Users\aux1\Documents\loquedejolatormenta\webswing-examples-eval-25.2.5"
echo Starting WebSwing Server for Soltec POS...
echo Access it at: http://localhost:8888/soltec
java -jar server/webswing-jetty-launcher.jar -j jetty.properties -serveradmin -pfa admin/webswing-admin.properties -adminctx /admin -aw admin/webswing-admin-server.war
