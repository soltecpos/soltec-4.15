@echo off
set DIRNAME=%~dp0
set CP="%DIRNAME%unicentaopos.jar"
set CP=%CP%;"%DIRNAME%lib/*"


start /B javaw -cp %CP% com.openbravo.pos.licensing.KeyGen
