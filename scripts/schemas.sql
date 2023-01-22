--SET PASSWORD FOR 'root'@'%' = PASSWORD('');
CREATE SCHEMA IF NOT EXISTS fleet_db_dev;
CREATE SCHEMA IF NOT EXISTS fleet_db_test;
GRANT ALL PRIVILEGES ON fleet_db_dev.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON fleet_db_test.* TO 'root'@'%';
FLUSH PRIVILEGES;
