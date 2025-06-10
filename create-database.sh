#!/bin/bash

echo "Creating PostgreSQL database 'product-upwork'..."
echo

# Check if psql is available
if ! command -v psql &> /dev/null; then
    echo "Error: PostgreSQL command-line tool (psql) not found."
    echo "Please make sure PostgreSQL is installed and added to your PATH."
    echo
    read -p "Press Enter to continue..."
    exit 1
fi

# Prompt for PostgreSQL password
read -sp "Enter PostgreSQL password for user 'postgres': " PGPASSWORD
echo
export PGPASSWORD

# Run the database creation script
if psql -U postgres -c "DO \$\$ BEGIN IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'product-upwork') THEN CREATE DATABASE \"product-upwork\"; END IF; END \$\$;"; then
    echo
    echo "Database 'product-upwork' created successfully or already exists!"
else
    echo
    echo "Failed to create database. Please check the error message above."
fi

echo
read -p "Press Enter to continue..."
