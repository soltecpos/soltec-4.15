@echo off
REM    SOLTEC POS - Touch Friendly Point of Sales designed for Touch Screen
REM    Copyright (c) 2009-2016 SOLTEC
REM    http://www.soltec.com
REM
REM    This file is part of SOLTEC POS
REM
REM    SOLTEC POS is free software: you can redistribute it and/or modify
REM    it under the terms of the GNU General Public License as published by
REM    the Free Software Foundation, either version 3 of the License, or
REM    (at your option) any later version.
REM
REM    SOLTEC POS is distributed in the hope that it will be useful,
REM    but WITHOUT ANY WARRANTY; without even the implied warranty of
REM    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
REM    GNU General Public License for more details.
REM
REM    You should have received a copy of the GNU General Public License
REM    along with SOLTEC POS.  If not, see http://www.gnu.org/licenses/>
REM

set DIRNAME=%~dp0
set CP="%DIRNAME%unicentaopos.jar"
set CP=%CP%;"%DIRNAME%lib/*"
set CP=%CP%;"%DIRNAME%lib/flatlaf-3.2.5.jar"
set CP=%CP%;"%DIRNAME%locales/"
set CP=%CP%;"%DIRNAME%reports/"

REM Apache Axis SOAP libraries.
set CP=%CP%;"%DIRNAME%lib/axis.jar"
set CP=%CP%;"%DIRNAME%lib/jaxrpc.jar"
set CP=%CP%;"%DIRNAME%lib/saaj.jar"
set CP=%CP%;"%DIRNAME%lib/wsdl4j-1.5.1.jar"

set CP=%CP%;"%DIRNAME%locales/"
set CP=%CP%;"%DIRNAME%reports/"

REM Check for bundled JRE
set "BUNDLED_JRE=%DIRNAME%jre"
set "JAVA_EXEC=javaw"

if exist "%BUNDLED_JRE%\bin\java.exe" (
    echo Using Bundled JRE: %BUNDLED_JRE%
    set "JAVA_EXEC="%BUNDLED_JRE%\bin\javaw.exe""
)

start /B "" %JAVA_EXEC% -Xms512m -Xmx1024m -cp %CP% -Djava.library.path="%DIRNAME%lib/Windows/i368-mingw32" -Ddirname.path="%DIRNAME%./" com.openbravo.pos.forms.StartPOS %1
