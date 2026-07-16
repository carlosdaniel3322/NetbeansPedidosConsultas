@echo off
echo ============================
echo Ejecutando pruebas...
echo ============================

call mvn test

echo.
echo Reporte generado en:

echo target\site\jacoco\index.html

pause