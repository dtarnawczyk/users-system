create table customers (
    id bigint generated by default as identity,
    active boolean not null,
    address varchar(255),
    created date,
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    login varchar(255),
    modified date,
    password varchar(255),
    customer_group varchar(255),
    primary key (id)
);