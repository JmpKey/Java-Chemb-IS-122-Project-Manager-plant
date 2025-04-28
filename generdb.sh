#!/bin/bash

echo "This script generates a database. If the database already exists, it will be cleaned!"
read -p "Are you sure you want to continue? (y/n): " answer

# Checking the user's response
if [[ "$answer" == "n" || "$answer" == "N" ]]; then
    echo "The script is completed by the user."
    exit 1
fi

# The path to the database file
DB_FILE="/tmp/ptdb.fdb"
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

# We connect to the created database and create tables
sudo -u firebird ./isql -user SYSDBA -password masterkey "$(realpath "$DB_FILE")" <<EOL
-- Deleting existing tables and sequences, if any
DROP TABLE TASKS IF EXISTS;
DROP TABLE USERS IF EXISTS;
DROP SEQUENCE SEQ_TASKS_ID_TASK IF EXISTS;
DROP SEQUENCE SEQ_USERS_ID_US IF EXISTS;

CREATE TABLE USERS (
    ID_US INTEGER NOT NULL,
    NAME_US VARCHAR(255),
    EMAIL_US VARCHAR(255),
    PASSW VARCHAR(255),
    CONSTRAINT PK_USERS PRIMARY KEY (ID_US),
    CONSTRAINT UQ_USERS_1 UNIQUE (NAME_US)
);

CREATE TABLE TASKS (
    ID_TASK INTEGER NOT NULL,
    NAME_TASK VARCHAR(255),
    TEXT_TASK VARCHAR(500),
    DETHLINE_TASK TIMESTAMP,
    CREATED_TASK TIMESTAMP,
    STATUS_TASK VARCHAR(3),
    EXEC_TASK BOOLEAN,
    LAST_CORRECT_TASK TIMESTAMP,
    ASSIGNED_TASK INTEGER,
    DEPENDENCIES_TASK VARCHAR(11) CHARACTER SET NONE,
    CONSTRAINT FK_TASKS_1 FOREIGN KEY (ASSIGNED_TASK) REFERENCES USERS (ID_US),
    CONSTRAINT PK_TASKS PRIMARY KEY (ID_TASK)
);

CREATE OR ALTER SEQUENCE SEQ_TASKS_ID_TASK START WITH 0 INCREMENT BY 1;
CREATE OR ALTER SEQUENCE SEQ_USERS_ID_US START WITH 0 INCREMENT BY 1;

SET TERM ^;
CREATE OR ALTER TRIGGER TASKS_BI
  FOR TASKS BEFORE INSERT
AS BEGIN END^

SET TERM ;^

SET TERM ^;
CREATE OR ALTER TRIGGER USERS_BI
  FOR USERS BEFORE INSERT
AS BEGIN END^

SET TERM ;^

SET TERM ^;
CREATE OR ALTER TRIGGER TASKS_BI FOR TASKS
ACTIVE BEFORE INSERT POSITION 0
AS
BEGIN
IF (NEW.ID_TASK IS NULL) THEN
NEW.ID_TASK = GEN_ID(SEQ_TASKS_ID_TASK,1);
END^
SET TERM ;^

SET TERM ^;
CREATE OR ALTER TRIGGER USERS_BI FOR USERS
ACTIVE BEFORE INSERT POSITION 0
AS
BEGIN
IF (NEW.ID_US IS NULL) THEN
NEW.ID_US = GEN_ID(SEQ_USERS_ID_US,1);
END^
SET TERM ;^

EOL

# Checking the successful creation of tables
if [ $? -eq 0 ]; then
    echo "The database has been successfully prepared."
else
    echo "Error when creating tables."
fi

