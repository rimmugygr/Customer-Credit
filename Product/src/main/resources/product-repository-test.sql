create table if not exists  product (
    id int NOT NULL AUTO_INCREMENT,
    credit_id int,
    product_value varchar(100),
    product_name varchar(100),
    primary key (id)
-- foreign key (credit_id) references creditdb.credit(id)
);
