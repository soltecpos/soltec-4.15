@echo off
set DIRNAME=%~dp0
set CP="%DIRNAME%smartpos.jar"
start /B javaw -cp %CP% com.smartpos.orderpop.OrderPop