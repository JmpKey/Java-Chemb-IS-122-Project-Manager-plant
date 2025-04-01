# PlanT
Project Manager and Task Dependency Scheduler

# How to collect it?
1. Copy the database from the project files ptdb.fdb to Unix: /tmp/ or /var/rdb (recommended). In Windows NT, it doesn't matter where the database is located. Execute from a standard user:
```
cp <Path where the downloaded ABC file is located ptdb.fdb> /tmp/
cd /tmp
```
2.  The example uses Firebird, and other databases may have an individual order. Changing the user to firebird:
```
sudo chown firebird:firebird ptdb.fdb
```
Add a connection called "ptdb_02" to the DBMS.
Note: Use Jaybird 5 as a driver.

3. Deploying a project to an IDE:
Open the project in the IDE. Java FXML can be added through the standard dependency management system.
Adding a driver: right-click on the project name (on the left side where the file hierarchy is shown) -> Open Module Settings -> Libraries -> Add (+) -> The jar file is in the project: */plant/dllhell/jaybird-5.0.6.java11/jaybird-5.0.6.java11.jar -> Click Apply -> Click OK.

Adding javax mail: just like the driver above, the path to the library is: */plant/dllhell/javax-mail-1.6.2/javax.mail.jar

0. How can I free the Database from firebird?:
```
sudo chmod 775 ptdb.fdb
```

# Configuration
Structure [property] = [parameter]

Etdit the */plant/connect.conf file:
dbname - DBMS name,
dbhost - physical location property,
dbport - port,
dbsu - database superuser name,
dbsupass - database superuser role,
dbcname - database path (in Windows NT must be replaced).

Etdit the */plant/mail.conf file (initial email support):
mail_smtp_host - SMTP host address,
mail_smtp_port - SMTP port,
mail_smtp_auth - authentication,
mail_smtp_starttls_enable - secure message transmission,
power_mail - enable or disable working with email.

# Dependencies
* jaybird (LGPL 2.1 or later, BSD 3-clause): https://github.com/FirebirdSQL/jaybird

* javax-mail (GPL 2.0, CDDL 1.1): https://github.com/javaee/javamail

* openjfx (GPL 2.0): https://github.com/openjdk/jfx?tab=readme-ov-file
