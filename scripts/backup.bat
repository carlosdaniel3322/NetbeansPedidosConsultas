@echo off

set FECHA=%date:~6,4%-%date:~3,2%-%date:~0,2%

if not exist "..\backups" mkdir "..\backups"

pg_dump -U postgres -F c -d sgpt > "..\backups\backup_%FECHA%.backup"

echo Backup realizado.

pause