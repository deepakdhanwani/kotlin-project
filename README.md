# Product Management Application

## Database Setup

### Issue: "FATAL: database 'product-upwork' does not exist"

If you encounter this error when starting the application, it means the PostgreSQL database `product-upwork` has not been created yet or Flyway cannot connect to it properly.

### Solution

Before running the application, you need to create the database manually. There are three ways to do this:

#### Option 1: Using the convenience scripts

**For Windows users:**
1. Simply run the `create-database.bat` file in the project root directory
2. Enter your PostgreSQL password when prompted
3. The script will create the database for you

**For Linux/Mac users:**
1. Make the script executable: `chmod +x create-database.sh`
2. Run the script: `./create-database.sh`
3. Enter your PostgreSQL password when prompted
4. The script will create the database for you

#### Option 2: Using the provided SQL script

1. Navigate to the `src/main/resources/db` directory
2. Run the `create-database.sql` script using PostgreSQL:
   ```
   psql -U postgres -f create-database.sql
   ```
   You will be prompted for the PostgreSQL password.

#### Option 3: Using PostgreSQL commands directly

1. Connect to PostgreSQL:
   ```
   psql -U postgres
   ```
   Enter your password when prompted.

2. Create the database:
   ```sql
   CREATE DATABASE "product-upwork";
   ```

3. Exit PostgreSQL:
   ```
   \q
   ```

## Running the Application

After creating the database, you can start the application normally. The Flyway migrations will automatically create the necessary tables and schema.

## Configuration

The database connection is configured in `src/main/resources/application.properties`. If you need to use different database credentials or settings, update this file accordingly.

### Flyway Configuration

Flyway is configured in the `application.properties` file with the following settings:

```properties
# Flyway Configuration
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true
spring.flyway.locations=classpath:db/migration
spring.flyway.url=${spring.datasource.url}
spring.flyway.user=${spring.datasource.username}
spring.flyway.password=${spring.datasource.password}
```

These settings ensure that:
1. Flyway is enabled
2. Flyway will baseline an existing database if necessary
3. Migration scripts are located in the `db/migration` directory
4. Flyway uses the same database connection as the main application

If you encounter issues with Flyway, make sure:
1. The database exists before starting the application
2. The database user has the necessary permissions
3. The database connection details are correct
