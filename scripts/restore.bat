@echo off

echo Restaurando respaldo...

pg_restore -U postgres -d sgpt "..\backups\backup.backup"

pause