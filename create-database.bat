@echo off
echo Creating PostgreSQL database 'product-upwork'...
echo.

REM Check if psql is available
where psql >nul 2>nul
if %ERRORLEVEL% neq 0 (
    echo Error: PostgreSQL command-line tool (psql) not found.
    echo Please make sure PostgreSQL is installed and added to your PATH.
    echo.
    pause
    exit /b 1
)

REM Prompt for PostgreSQL password
set /p PGPASSWORD="Enter PostgreSQL password for user 'postgres': "

REM Run the database creation script
psql -U postgres -c "DO $$ BEGIN IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'product-upwork') THEN CREATE DATABASE \"product-upwork\"; END IF; END $$;"

if %ERRORLEVEL% equ 0 (
    echo.
    echo Database 'product-upwork' created successfully or already exists!
) else (
    echo.
    echo Failed to create database. Please check the error message above.
)

echo.
pause
