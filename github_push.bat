@echo off
set GIT_EXE="C:\Program Files\Git\cmd\git.exe"
cd /d "C:\Users\aux1\Documents\loquedejolatormenta"
%GIT_EXE% rm -rf --cached .
%GIT_EXE% add .
%GIT_EXE% commit -m "Backup version 26021246: Ultima version estable del codigo fuente."
