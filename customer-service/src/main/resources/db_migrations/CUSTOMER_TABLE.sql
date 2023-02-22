create table customers_table (
    id int8 primary key not null auto_increment,
    name varchar(100) default null,
    address text default null,
    city char default null,
    province char default null,
    registration_date timestamp default current_timestamp,
    status char default null
);