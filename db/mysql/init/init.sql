-- CREATE USER 'user'@'localhost' IDENTIFIED BY 'user';
-- GRANT ALL PRIVILEGES ON creditdb.* TO 'user'@'localhost';
GRANT ALL PRIVILEGES ON *.* TO 'user'@'%';

CREATE SCHEMA `creditdb` ;
use creditdb;
-- drop table if exists  credit;
create table if not exists credit (
id int NOT NULL AUTO_INCREMENT,
credit_name varchar(100),
primary key (id)
);

CREATE SCHEMA `productdb` ;
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

CREATE SCHEMA `customerdb` ;
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