use creditdb;
-- drop table if exists  credit;
create table if not exists credit (
id int,
credit_name varchar(100),
primary key (id));

use productdb;
-- drop table if exists  product;
create table if not exists  product (
id int,
credit_id int,
product_value varchar(100),
product_name varchar(100),
primary key (id),
foreign key (credit_id) references creditdb.credit(id));

use customerdb;
-- drop table if exists  costumer; 
create table if not exists customer (
id int,
credit_id int,
first_name varchar(100),
surname varchar(100),
pesel varchar(100),
primary key (id),
foreign key (credit_id) references creditdb.credit(id))