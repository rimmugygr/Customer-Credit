create table if not exists customer (
    id int NOT NULL AUTO_INCREMENT,
    credit_id int,
    first_name varchar(100),
    surname varchar(100),
    pesel varchar(100),
    primary key (id)
-- foreign key (credit_id) references creditdb.credit(id)
);
