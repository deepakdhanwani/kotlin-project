-- PostgreSQL script to create the product-upwork database
-- Run this script before starting the application if the database doesn't exist

-- Connect to the default 'postgres' database first
\c postgres;

-- Check if the database already exists and create it if it doesn't
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'product-upwork') THEN
        -- Create the database if it doesn't exist
        CREATE DATABASE "product-upwork";
    END IF;
END
$$;
