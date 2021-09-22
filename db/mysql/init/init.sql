CREATE USER 'credit'@'%' IDENTIFIED BY 'credit';
GRANT ALL PRIVILEGES ON creditdb.* TO 'credit'@'%';

CREATE USER 'product'@'%' IDENTIFIED BY 'product';
GRANT ALL PRIVILEGES ON productdb.* TO 'product'@'%';

CREATE USER 'customer'@'%' IDENTIFIED BY 'customer';
GRANT ALL PRIVILEGES ON customerdb.* TO 'customer'@'%';

GRANT ALL PRIVILEGES ON *.* TO 'user'@'%';

CREATE SCHEMA if not exists `creditdb`;
use creditdb;
-- drop table if exists  credit;
create table if not exists credit (
id int NOT NULL AUTO_INCREMENT,
credit_name varchar(100),
primary key (ID)
);

CREATE SCHEMA if not exists `productdb`;
use productdb;
-- drop table if exists  product;
create table if not exists  product (
id int NOT NULL AUTO_INCREMENT,
credit_id int,
product_value varchar(100),
product_name varchar(100),
primary key (id)
-- foreign key (credit_id) references creditdb.credit(id)
);

CREATE SCHEMA if not exists `customerdb`;
use customerdb;
-- drop table if exists  customer; 
create table if not exists customer (
id int NOT NULL AUTO_INCREMENT,
credit_id int,
first_name varchar(100),
surname varchar(100),
pesel varchar(100),
primary key (id)
-- foreign key (credit_id) references creditdb.credit(id)
)