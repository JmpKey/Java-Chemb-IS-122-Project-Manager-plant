#!/bin/bash

# The path to the SQL files
SQL_DIR="$(dirname "$(realpath "$0")")/sql" # Specify the path to the directory with the SQL files here
DB_FILE="/tmp/db.fdb"
echo "Running an SQL script to prepare the database..."

# Creating a database
sudo -u firebird ./isql -user SYSDBA -password masterkey <<EOL
CREATE DATABASE '$(realpath "$DB_FILE")' page_size 8192;
EOL

# Checking the database creation
if [ $? -ne 0 ]; then
    echo "Error when creating the database."
    exit 1
fi

# A function for executing SQL files
execute_sql_files() {
    for sql_file in "$SQL_DIR"/*.sql; do
        echo "File Execution: $sql_file"
        sudo -u firebird ./isql -user SYSDBA -password masterkey "$(realpath "$DB_FILE")" < "$sql_file"
        if [ $? -ne 0 ]; then
            echo "File execution error: $sql_file"
            exit 1
        fi
    done
}

# Starting the execution of SQL files
execute_sql_files

echo "The database has been successfully prepared."

